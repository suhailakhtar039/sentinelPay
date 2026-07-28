package com.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummary {
    private Long totalTransactions;
    private BigDecimal totalVolume;
    private BigDecimal averageTransactionAmount;
    private Long successfulTransactions;
    private Long failedTransactions;
    private Long pendingTransactions;
}