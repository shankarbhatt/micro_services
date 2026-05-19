package com.stocktrading.settlement.service;

import com.stocktrading.settlement.entity.Settlement;
import com.stocktrading.settlement.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository settlementRepository;

    @Transactional
    public Settlement createSettlement(Settlement settlement) {
        if (settlementRepository.existsByTradeId(settlement.getTradeId())) {
            throw new RuntimeException("Settlement already exists for trade: " + settlement.getTradeId());
        }

        settlement.setBrokerage(settlement.getTotalAmount().multiply(new BigDecimal("0.0005")));
        settlement.setTaxes(settlement.getTotalAmount().multiply(new BigDecimal("0.001")));
        settlement.setNetAmount(settlement.getTotalAmount()
                .add(settlement.getBrokerage())
                .add(settlement.getTaxes()));
        settlement.setStatus(Settlement.SettlementStatus.PENDING);
        settlement.setSettlementReferenceNumber(generateReferenceNumber());

        return settlementRepository.save(settlement);
    }

    public List<Settlement> getAllSettlements() {
        return settlementRepository.findAll();
    }

    public Optional<Settlement> getSettlementById(Long id) {
        return settlementRepository.findById(id);
    }

    public Optional<Settlement> getSettlementByTradeId(Long tradeId) {
        return settlementRepository.findByTradeId(tradeId);
    }

    public List<Settlement> getSettlementsByAccountId(Long accountId) {
        return settlementRepository.findByAccountId(accountId);
    }

    public List<Settlement> getSettlementsByStatus(Settlement.SettlementStatus status) {
        return settlementRepository.findByStatus(status);
    }

    @Transactional
    public Settlement processSettlement(Long settlementId) {
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new RuntimeException("Settlement not found with id: " + settlementId));

        if (settlement.getStatus() != Settlement.SettlementStatus.PENDING) {
            throw new RuntimeException("Settlement can only be processed if it is in PENDING status");
        }

        settlement.setStatus(Settlement.SettlementStatus.IN_PROGRESS);
        settlement = settlementRepository.save(settlement);

        settlement.setStatus(Settlement.SettlementStatus.COMPLETED);
        return settlementRepository.save(settlement);
    }

    @Transactional
    public Settlement failSettlement(Long settlementId, String reason) {
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new RuntimeException("Settlement not found with id: " + settlementId));

        settlement.setStatus(Settlement.SettlementStatus.FAILED);
        return settlementRepository.save(settlement);
    }

    @Transactional
    public Settlement cancelSettlement(Long settlementId) {
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new RuntimeException("Settlement not found with id: " + settlementId));

        if (settlement.getStatus() == Settlement.SettlementStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel completed settlement");
        }

        settlement.setStatus(Settlement.SettlementStatus.CANCELLED);
        return settlementRepository.save(settlement);
    }

    private String generateReferenceNumber() {
        return "STL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
