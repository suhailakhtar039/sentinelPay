package com.paymentservice.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverviewAnalyticsResponse {
    long totalPayments;
    long successfulPayments;
    long failedPayments;
    BigDecimal totalVolume;
    BigDecimal averageTransactionAmount;
}
