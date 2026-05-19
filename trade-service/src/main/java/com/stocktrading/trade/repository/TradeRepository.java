package com.stocktrading.trade.repository;

import com.stocktrading.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByAccountId(Long accountId);
    List<Trade> findByStatus(Trade.TradeStatus status);
    List<Trade> findByAccountIdAndStatus(Long accountId, Trade.TradeStatus status);
    List<Trade> findByStockSymbol(String stockSymbol);
    List<Trade> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
