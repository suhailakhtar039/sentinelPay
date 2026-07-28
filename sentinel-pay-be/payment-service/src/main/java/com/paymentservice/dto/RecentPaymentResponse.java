package com.paymentservice.dto;

import com.paymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecentPaymentResponse {
    private Long paymentId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal amount;

    private String currency;

    private PaymentStatus status;

    private LocalDateTime createdAt;
}
