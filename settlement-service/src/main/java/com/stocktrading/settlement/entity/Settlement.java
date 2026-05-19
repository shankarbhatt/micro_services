package com.stocktrading.settlement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tradeId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long dematAccountId;

    @Column(nullable = false)
    private String stockSymbol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SettlementType settlementType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal pricePerShare;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private BigDecimal brokerage;

    @Column(nullable = false)
    private BigDecimal taxes;

    @Column(nullable = false)
    private BigDecimal netAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SettlementStatus status;

    private String settlementReferenceNumber;

    private LocalDateTime settlementDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum SettlementType {
        BUY, SELL
    }

    public enum SettlementStatus {
        PENDING, IN_PROGRESS, COMPLETED, FAILED, CANCELLED
    }

	
	
}
