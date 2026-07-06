package com.paymentservice.projection.analytics;

import java.math.BigDecimal;

public interface MonthlyVolumeProjection {
    String getMonth();
    BigDecimal getTotalVolume();
}
