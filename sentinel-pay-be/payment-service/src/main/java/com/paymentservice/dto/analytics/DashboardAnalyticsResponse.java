package com.paymentservice.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardAnalyticsResponse {
    private OverviewAnalyticsResponse overview;

    private AverageAmountResponse averageTransaction;

    private List<MonthlyVolumeResponse> monthlyVolume;

    private List<DailyTransactionResponse> dailyTransactions;

    private List<PaymentStatusResponse> paymentStatusDistribution;

    private List<TopReceiverResponse> topReceivers;

}
