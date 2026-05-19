package com.stocktrading.demat.repository;

import com.stocktrading.demat.entity.DematAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DematAccountRepository extends JpaRepository<DematAccount, Long> {
    Optional<DematAccount> findByDematAccountNumber(String dematAccountNumber);
    Optional<DematAccount> findByAccountId(Long accountId);
    boolean existsByDematAccountNumber(String dematAccountNumber);
}
