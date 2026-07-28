package com.paymentservice.dashboard.impl;

import com.paymentservice.dashboard.DashboardService;
import com.paymentservice.dto.DashboardResponse;
import com.paymentservice.dto.DashboardSummary;
import com.paymentservice.dto.PaymentResponse;
import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;
import com.paymentservice.service.AnalyticsService;
import com.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final AnalyticsService analyticsService;
    private final PaymentService paymentService;

    @Override
    public DashboardResponse getDashboard(Long userId) {

        OverviewAnalyticsResponse overview =
                analyticsService.getOverviewAnalytics(userId);

        List<DailyTransactionResponse> dailyTransactions =
                analyticsService.getDailyTransactions(userId);

        List<MonthlyVolumeResponse> monthlyVolume =
                analyticsService.getMonthlyPaymentVolume(userId);

        List<PaymentStatusResponse> paymentStatus =
                analyticsService.getPaymentStatusDistribution(userId);

        List<TopReceiverResponse> topReceivers =
                analyticsService.getTopReceivers(userId);

        AverageAmountResponse averageAmount =
                analyticsService.getAverageTransactionAmount(userId);

        List<PaymentResponse> recentPayments =
                paymentService.getMyPayments(userId);

        DashboardSummary summary =
                DashboardSummary.builder()
                        .totalTransactions(overview.getTotalPayments())
                        .totalVolume(overview.getTotalVolume())
                        .averageTransactionAmount(
                                overview.getAverageTransactionAmount())
                        .successfulTransactions(
                                overview.getSuccessfulPayments())
                        .failedTransactions(
                                overview.getFailedPayments())
                        .pendingTransactions(
                                overview.getPendingPayments())
                        .build();

        return DashboardResponse.builder()
                .summary(summary)
                .recentPayments(recentPayments)
                .dailyTransactions(dailyTransactions)
                .monthlyVolume(monthlyVolume)
                .paymentStatus(paymentStatus)
                .topReceivers(topReceivers)
                .averageAmount(averageAmount)
                .build();
    }
}
