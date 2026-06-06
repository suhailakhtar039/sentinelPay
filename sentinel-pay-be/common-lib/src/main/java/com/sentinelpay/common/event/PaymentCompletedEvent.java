package com.sentinelpay.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {

    private Long paymentId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal amount;

    private String currency;
}
