package com.stocktrading.settlement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "trade-service", url = "${trade-service.url}")
public interface TradeClient {

    @GetMapping("/api/trades/{id}")
    TradeDto getTradeById(@PathVariable("id") Long id);

    record TradeDto(Long id, Long accountId, Long dematAccountId, String stockSymbol,
                    String tradeType, Integer quantity, BigDecimal price, BigDecimal totalAmount,
                    String status) {}
}
