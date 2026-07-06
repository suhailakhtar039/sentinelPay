package com.paymentservice.service;

import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;

import java.util.List;

public interface AnalyticsService {
    OverviewAnalyticsResponse getOverviewAnalytics();

    List<DailyTransactionResponse> getDailyTransactions();

    List<MonthlyVolumeResponse> getMonthly();

    List<PaymentStatusResponse> getPaymentStatusDistribution();

    List<TopReceiverResponse> getTopReceivers();

    AverageAmountResponse getAverageTransactionResponse();
}
