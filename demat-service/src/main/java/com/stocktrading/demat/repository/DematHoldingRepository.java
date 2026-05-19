package com.stocktrading.demat.repository;

import com.stocktrading.demat.entity.DematHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DematHoldingRepository extends JpaRepository<DematHolding, Long> {
    List<DematHolding> findByDematAccountId(Long dematAccountId);
    Optional<DematHolding> findByDematAccountIdAndStockSymbol(Long dematAccountId, String stockSymbol);
    Optional<DematHolding> findByDematAccountIdAndIsin(Long dematAccountId, String isin);
    boolean existsByDematAccountIdAndStockSymbol(Long dematAccountId, String stockSymbol);
}
