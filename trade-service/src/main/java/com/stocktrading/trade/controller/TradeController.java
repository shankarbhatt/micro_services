package com.stocktrading.trade.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocktrading.trade.entity.Trade;
import com.stocktrading.trade.service.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<Trade> placeTrade(@RequestBody Trade trade) {
        Trade created = tradeService.placeTrade(trade);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        return ResponseEntity.ok(tradeService.getAllTrades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable Long id) {
        return tradeService.getTradeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Trade>> getTradesByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(tradeService.getTradesByAccountId(accountId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Trade>> getTradesByStatus(@PathVariable Trade.TradeStatus status) {
        return ResponseEntity.ok(tradeService.getTradesByStatus(status));
    }

    @GetMapping("/account/{accountId}/status/{status}")
    public ResponseEntity<List<Trade>> getTradesByAccountAndStatus(
            @PathVariable Long accountId,
            @PathVariable Trade.TradeStatus status) {
        return ResponseEntity.ok(tradeService.getTradesByAccountIdAndStatus(accountId, status));
    }

    @GetMapping("/stock/{stockSymbol}")
    public ResponseEntity<List<Trade>> getTradesByStockSymbol(@PathVariable String stockSymbol) {
        return ResponseEntity.ok(tradeService.getTradesByStockSymbol(stockSymbol));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Trade>> getTradesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(tradeService.getTradesByDateRange(start, end));
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<Trade> executeTrade(@PathVariable Long id) {
        Trade executed = tradeService.executeTrade(id);
        return ResponseEntity.ok(executed);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Trade> cancelTrade(@PathVariable Long id) {
        Trade cancelled = tradeService.cancelTrade(id);
        return ResponseEntity.ok(cancelled);
    }

    @PostMapping("/{id}/settle")
    public ResponseEntity<Trade> settleTrade(@PathVariable Long id) {
        Trade settled = tradeService.markAsSettled(id);
        return ResponseEntity.ok(settled);
    }
}
