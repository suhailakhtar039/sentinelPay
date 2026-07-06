package com.paymentservice.projection.analytics;

import java.time.LocalDate;

public interface DailyTransactionProjection {
    LocalDate getDate();
    Long getTransactionCount();
}
