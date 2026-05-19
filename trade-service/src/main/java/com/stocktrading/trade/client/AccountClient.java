package com.stocktrading.trade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "account-service", url = "${account-service.url}")
public interface AccountClient {

    @GetMapping("/api/accounts/{id}")
    AccountDto getAccountById(@PathVariable("id") Long id);

    @GetMapping("/api/accounts/{id}/check-balance")
    Boolean checkSufficientBalance(@PathVariable("id") Long id, @RequestParam("amount") BigDecimal amount);

    record AccountDto(Long id, String accountNumber, String accountHolderName, BigDecimal balance) {}
}
