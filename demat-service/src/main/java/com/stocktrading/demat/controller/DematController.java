package com.stocktrading.demat.controller;

import com.stocktrading.demat.entity.DematAccount;
import com.stocktrading.demat.entity.DematHolding;
import com.stocktrading.demat.service.DematService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demat")
@RequiredArgsConstructor
public class DematController {

    private final DematService dematService;

    @PostMapping("/accounts")
    public ResponseEntity<DematAccount> createDematAccount(@RequestBody DematAccount dematAccount) {
        DematAccount created = dematService.createDematAccount(dematAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<DematAccount>> getAllDematAccounts() {
        return ResponseEntity.ok(dematService.getAllDematAccounts());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<DematAccount> getDematAccountById(@PathVariable Long id) {
        return dematService.getDematAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/accounts/user/{accountId}")
    public ResponseEntity<DematAccount> getDematAccountByAccountId(@PathVariable Long accountId) {
        return dematService.getDematAccountByAccountId(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/holdings")
    public ResponseEntity<DematHolding> addHolding(@RequestBody DematHolding holding) {
        DematHolding created = dematService.addHolding(holding);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/holdings/{dematAccountId}")
    public ResponseEntity<List<DematHolding>> getHoldings(@PathVariable Long dematAccountId) {
        return ResponseEntity.ok(dematService.getHoldingsByDematAccountId(dematAccountId));
    }

    @GetMapping("/holdings/{dematAccountId}/check")
    public ResponseEntity<Boolean> checkSufficientHoldings(
            @PathVariable Long dematAccountId,
            @RequestParam String stockSymbol,
            @RequestParam int quantity) {
        return ResponseEntity.ok(dematService.hasSufficientHoldings(dematAccountId, stockSymbol, quantity));
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteDematAccount(@PathVariable Long id) {
        dematService.deleteDematAccount(id);
        return ResponseEntity.noContent().build();
    }
}
