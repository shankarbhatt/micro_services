package com.stocktrading.settlement.repository;

import com.stocktrading.settlement.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    Optional<Settlement> findByTradeId(Long tradeId);
    List<Settlement> findByAccountId(Long accountId);
    List<Settlement> findByStatus(Settlement.SettlementStatus status);
    List<Settlement> findByStatusAndSettlementType(Settlement.SettlementStatus status, Settlement.SettlementType type); // ✅ FIXED
    boolean existsByTradeId(Long tradeId);
}