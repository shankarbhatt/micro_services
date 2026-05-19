package com.stocktrading.demat.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stocktrading.demat.entity.DematAccount;
import com.stocktrading.demat.entity.DematHolding;
import com.stocktrading.demat.repository.DematAccountRepository;
import com.stocktrading.demat.repository.DematHoldingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DematService {

    private final DematAccountRepository dematAccountRepository;
    private final DematHoldingRepository dematHoldingRepository;

    public DematAccount createDematAccount(DematAccount dematAccount) {
        if (dematAccountRepository.existsByDematAccountNumber(dematAccount.getDematAccountNumber())) {
            throw new RuntimeException("Demat account number already exists");
        }
        dematAccount.setStatus(DematAccount.DematStatus.ACTIVE);
        return dematAccountRepository.save(dematAccount);
    }

    public List<DematAccount> getAllDematAccounts() {
        return dematAccountRepository.findAll();
    }

    public Optional<DematAccount> getDematAccountById(Long id) {
        return dematAccountRepository.findById(id);
    }

    public Optional<DematAccount> getDematAccountByAccountId(Long accountId) {
        return dematAccountRepository.findByAccountId(accountId);
    }

    @SuppressWarnings("deprecation")
    @Transactional
    public DematHolding addHolding(DematHolding holding) {
        // ADD THIS — fetch the real DematAccount from DB first
        Long dematAccountId = holding.getDematAccount().getId();
        DematAccount dematAccount = dematAccountRepository.findById(dematAccountId)
                .orElseThrow(() -> new RuntimeException("Demat account not found with id: " + dematAccountId));
        
        // Set the fully loaded entity back
        holding.setDematAccount(dematAccount);

        Optional<DematHolding> existing = dematHoldingRepository.findByDematAccountIdAndStockSymbol(
                dematAccountId, holding.getStockSymbol());

        if (existing.isPresent()) {
            DematHolding current = existing.get();
            int totalQuantity = current.getQuantity() + holding.getQuantity();
            BigDecimal totalValue = current.getAveragePrice()
                    .multiply(BigDecimal.valueOf(current.getQuantity()))
                    .add(holding.getAveragePrice()
                    .multiply(BigDecimal.valueOf(holding.getQuantity())));
            current.setQuantity(totalQuantity);
            current.setAveragePrice(totalValue.divide(
                    BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_UP));
            return dematHoldingRepository.save(current);
        }

        return dematHoldingRepository.save(holding);
    }

    @Transactional
    public DematHolding reduceHolding(Long dematAccountId, String stockSymbol, int quantity) {
        DematHolding holding = dematHoldingRepository.findByDematAccountIdAndStockSymbol(dematAccountId, stockSymbol)
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        if (holding.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity in holdings");
        }

        holding.setQuantity(holding.getQuantity() - quantity);
        if (holding.getQuantity() == 0) {
            dematHoldingRepository.delete(holding);
            return null;
        }
        return dematHoldingRepository.save(holding);
    }

    public List<DematHolding> getHoldingsByDematAccountId(Long dematAccountId) {
        return dematHoldingRepository.findByDematAccountId(dematAccountId);
    }

    public boolean hasSufficientHoldings(Long dematAccountId, String stockSymbol, int quantity) {
        return dematHoldingRepository.findByDematAccountIdAndStockSymbol(dematAccountId, stockSymbol)
                .map(holding -> holding.getQuantity() >= quantity)
                .orElse(false);
    }

    public void deleteDematAccount(Long id) {
        if (!dematAccountRepository.existsById(id)) {
            throw new RuntimeException("Demat account not found with id: " + id);
        }
        dematAccountRepository.deleteById(id);
    }
}
