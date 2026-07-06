package com.paymentservice.projection.analytics;

import java.math.BigDecimal;

public interface OverviewAnalyticsProjection {
    Long getTotalPayments();
    Long getSuccessfulPayments();
    Long getFailedPayments();
    BigDecimal getTotalVolume();
    BigDecimal getAverageTransactionAmount();
}
