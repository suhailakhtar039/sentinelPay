package com.paymentservice.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopReceiverResponse {
    UUID receiverId;
    BigDecimal totalReceived;
    long transactionCount;
}
