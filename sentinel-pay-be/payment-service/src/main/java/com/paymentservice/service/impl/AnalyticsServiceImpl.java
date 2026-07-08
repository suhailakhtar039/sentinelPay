package com.paymentservice.service.impl;

import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;
import com.paymentservice.projection.analytics.AverageAmountProjection;
import com.paymentservice.projection.analytics.OverviewAnalyticsProjection;
import com.paymentservice.projection.analytics.PaymentStatusProjection;
import com.paymentservice.repository.PaymentRepository;
import com.paymentservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final PaymentRepository paymentRepository;

    @Override
    public OverviewAnalyticsResponse getOverviewAnalytics() {
        OverviewAnalyticsProjection projection =
                paymentRepository.getOverviewAnalytics();

        return new OverviewAnalyticsResponse(
                projection.getTotalPayments(),
                projection.getSuccessfulPayments(),
                projection.getFailedPayments(),
                projection.getPendingPayments(),
                projection.getTotalVolume(),
                projection.getAverageTransactionAmount()
        );
    }

    @Override
    public List<DailyTransactionResponse> getDailyTransactions() {
        return List.of();
    }

    @Override
    public List<MonthlyVolumeResponse> getMonthly() {
        return List.of();
    }

    @Override
    public List<PaymentStatusResponse> getPaymentStatusDistribution() {
        return paymentRepository.getPaymentStatusDistribution()
                .stream()
                .map(projection -> new PaymentStatusResponse(
                        projection.getStatus(),
                        projection.getCount()
                ))
                .toList();

    }

    @Override
    public List<TopReceiverResponse> getTopReceivers() {
        return List.of();
    }

    @Override
    public AverageAmountResponse getAverageTransactionResponse() {
        AverageAmountProjection averageTransaction = paymentRepository.getAverageTransaction();
        return new AverageAmountResponse(averageTransaction.getAverageAmount());
    }
}
