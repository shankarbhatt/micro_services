package com.stocktrading.account.service;

import com.stocktrading.account.entity.Account;
import com.stocktrading.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new RuntimeException("Account number already exists");
        }
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        // Remove the hardcoded zero — keep the balance from request
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO); // only default to 0 if not provided
        }
        if (account.getStatus() == null) {
            account.setStatus(Account.AccountStatus.ACTIVE); // only default if not provided
        }
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        account.setAccountHolderName(accountDetails.getAccountHolderName());
        account.setPhone(accountDetails.getPhone());
        account.setAccountType(accountDetails.getAccountType());

        // ADD THIS — allow balance update
        if (accountDetails.getBalance() != null) {
            account.setBalance(accountDetails.getBalance());
        }
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount must be positive");
        }

        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Withdrawal amount must be positive");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    public boolean hasSufficientBalance(Long accountId, BigDecimal amount) {
        return accountRepository.findById(accountId)
                .map(account -> account.getBalance().compareTo(amount) >= 0)
                .orElse(false);
    }
}
