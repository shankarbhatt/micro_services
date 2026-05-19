package com.stocktrading.trade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "demat-service", url = "${demat-service.url}")
public interface DematClient {

    @GetMapping("/api/demat/holdings/{dematAccountId}/check")
    Boolean checkSufficientHoldings(
            @PathVariable("dematAccountId") Long dematAccountId,
            @RequestParam("stockSymbol") String stockSymbol,
            @RequestParam("quantity") int quantity);

    @GetMapping("/api/demat/accounts/user/{accountId}")
    DematAccountDto getDematAccountByAccountId(@PathVariable("accountId") Long accountId);

    record DematAccountDto(Long id, String dematAccountNumber, Long accountId, String status) {}
}
