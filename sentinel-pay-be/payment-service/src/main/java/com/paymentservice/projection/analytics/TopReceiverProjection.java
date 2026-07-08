package com.paymentservice.projection.analytics;

import java.math.BigDecimal;

public interface TopReceiverProjection {
    long getReceiverId();
    BigDecimal getTotalReceived();
    Long getTransactionCount();
}
