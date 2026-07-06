package com.paymentservice.dto.analytics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class TopReceiverResponse {
    UUID receiverId;
    BigDecimal totalReceived;
    long transactionCount;
}
