package com.paymentservice.dto.analytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OverviewAnalyticsResponse {
    long totalPayments;
    long successfulPayments;
    long failedPayments;
    BigDecimal totalVolume;
    BigDecimal averageTransactionAmount;
}
