package com.paymentservice.dto;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        Long receiverUserId,
        BigDecimal amount,
        String currency
) {}
