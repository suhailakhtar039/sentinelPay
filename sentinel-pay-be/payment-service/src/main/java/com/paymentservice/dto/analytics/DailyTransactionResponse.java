package com.paymentservice.dto.analytics;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DailyTransactionResponse {
    LocalDate date;
    long transactionCount;
}
