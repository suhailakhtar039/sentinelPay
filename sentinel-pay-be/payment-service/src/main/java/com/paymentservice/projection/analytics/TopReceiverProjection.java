package com.paymentservice.projection.analytics;

import java.math.BigDecimal;
import java.util.UUID;

public interface TopReceiverProjection {
    UUID getReceiverId();
    BigDecimal getTotalReceived();
    Long getTransactionCount();
}
