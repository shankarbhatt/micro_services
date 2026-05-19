package com.stocktrading.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service", r -> r
                        .path("/api/accounts/**")
                        .uri("http://localhost:8081"))
                .route("demat-service", r -> r
                        .path("/api/demat/**")
                        .uri("http://localhost:8082"))
                .route("trade-service", r -> r
                        .path("/api/trades/**")
                        .uri("http://localhost:8083"))
                .route("settlement-service", r -> r
                        .path("/api/settlements/**")
                        .uri("http://localhost:8084"))
                .build();
    }
}
