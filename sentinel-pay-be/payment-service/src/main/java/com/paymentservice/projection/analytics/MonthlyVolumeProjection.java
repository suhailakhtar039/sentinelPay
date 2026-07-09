package com.paymentservice.projection.analytics;

import java.math.BigDecimal;

public interface MonthlyVolumeProjection {
    Integer getYear();

    Integer getMonth();

    BigDecimal getTotalVolume();
}
