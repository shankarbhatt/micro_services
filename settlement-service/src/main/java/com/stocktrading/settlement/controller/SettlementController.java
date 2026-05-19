package com.stocktrading.settlement.controller;

import com.stocktrading.settlement.entity.Settlement;
import com.stocktrading.settlement.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public ResponseEntity<Settlement> createSettlement(@RequestBody Settlement settlement) {
        Settlement created = settlementService.createSettlement(settlement);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Settlement>> getAllSettlements() {
        return ResponseEntity.ok(settlementService.getAllSettlements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Settlement> getSettlementById(@PathVariable Long id) {
        return settlementService.getSettlementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/trade/{tradeId}")
    public ResponseEntity<Settlement> getSettlementByTradeId(@PathVariable Long tradeId) {
        return settlementService.getSettlementByTradeId(tradeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Settlement>> getSettlementsByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(settlementService.getSettlementsByAccountId(accountId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Settlement>> getSettlementsByStatus(@PathVariable Settlement.SettlementStatus status) {
        return ResponseEntity.ok(settlementService.getSettlementsByStatus(status));
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<Settlement> processSettlement(@PathVariable Long id) {
        Settlement processed = settlementService.processSettlement(id);
        return ResponseEntity.ok(processed);
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<Settlement> failSettlement(@PathVariable Long id, @RequestParam String reason) {
        Settlement failed = settlementService.failSettlement(id, reason);
        return ResponseEntity.ok(failed);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Settlement> cancelSettlement(@PathVariable Long id) {
        Settlement cancelled = settlementService.cancelSettlement(id);
        return ResponseEntity.ok(cancelled);
    }
}
