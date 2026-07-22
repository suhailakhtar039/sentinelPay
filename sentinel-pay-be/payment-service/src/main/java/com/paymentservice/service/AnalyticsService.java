package com.paymentservice.service;

import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.DashboardAnalyticsResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;

import java.util.List;

public interface AnalyticsService {
    DashboardAnalyticsResponse getDashboardAnalytics(Long userId);

    OverviewAnalyticsResponse getOverviewAnalytics(Long userId);

    List<MonthlyVolumeResponse> getMonthlyPaymentVolume(Long userId);

    List<DailyTransactionResponse> getDailyTransactions(Long userId);

    List<PaymentStatusResponse> getPaymentStatusDistribution(Long userId);

    List<TopReceiverResponse> getTopReceivers(Long userId);

    AverageAmountResponse getAverageTransactionAmount(Long userId);
}
