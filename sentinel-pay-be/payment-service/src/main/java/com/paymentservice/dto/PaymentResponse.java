package com.paymentservice.dto;

import com.paymentservice.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime createdAt;

}
