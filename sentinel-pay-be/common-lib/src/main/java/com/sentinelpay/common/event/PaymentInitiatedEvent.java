package com.sentinelpay.common.event;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentInitiatedEvent {

    private Long paymentId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal amount;
}
