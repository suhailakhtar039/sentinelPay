package com.paymentservice.dto.analytics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentStatusResponse {
    String status;
    long count;
}
