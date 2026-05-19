package com.stocktrading.trade.service;

import com.stocktrading.trade.entity.Trade;
import com.stocktrading.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    @Transactional
    public Trade placeTrade(Trade trade) {
        trade.setTotalAmount(trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
        trade.setStatus(Trade.TradeStatus.PENDING);
        return tradeRepository.save(trade);
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(Long id) {
        return tradeRepository.findById(id);
    }

    public List<Trade> getTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountId(accountId);
    }

    public List<Trade> getTradesByStatus(Trade.TradeStatus status) {
        return tradeRepository.findByStatus(status);
    }

    public List<Trade> getTradesByAccountIdAndStatus(Long accountId, Trade.TradeStatus status) {
        return tradeRepository.findByAccountIdAndStatus(accountId, status);
    }

    public List<Trade> getTradesByStockSymbol(String stockSymbol) {
        return tradeRepository.findByStockSymbol(stockSymbol);
    }

    public List<Trade> getTradesByDateRange(LocalDateTime start, LocalDateTime end) {
        return tradeRepository.findByCreatedAtBetween(start, end);
    }

    @Transactional
    public Trade executeTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found with id: " + tradeId));

        if (trade.getStatus() != Trade.TradeStatus.PENDING) {
            throw new RuntimeException("Trade can only be executed if it is in PENDING status");
        }

        trade.setStatus(Trade.TradeStatus.EXECUTED);
        trade.setExecutedAt(LocalDateTime.now());
        return tradeRepository.save(trade);
    }

    @Transactional
    public Trade cancelTrade(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found with id: " + tradeId));

        if (trade.getStatus() == Trade.TradeStatus.EXECUTED || trade.getStatus() == Trade.TradeStatus.SETTLED) {
            throw new RuntimeException("Cannot cancel executed or settled trade");
        }

        trade.setStatus(Trade.TradeStatus.CANCELLED);
        return tradeRepository.save(trade);
    }

    @Transactional
    public Trade markAsSettled(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new RuntimeException("Trade not found with id: " + tradeId));

        trade.setStatus(Trade.TradeStatus.SETTLED);
        return tradeRepository.save(trade);
    }
}
