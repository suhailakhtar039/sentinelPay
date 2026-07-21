package com.paymentservice.service.impl;

import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.DashboardAnalyticsResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;
import com.paymentservice.projection.analytics.AverageAmountProjection;
import com.paymentservice.projection.analytics.OverviewAnalyticsProjection;
import com.paymentservice.projection.analytics.TopReceiverProjection;
import com.paymentservice.repository.PaymentRepository;
import com.paymentservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.paymentservice.config.CacheNames.ANALYTICS;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final PaymentRepository paymentRepository;

    @Override
    @Cacheable(cacheNames = ANALYTICS, key = "'overview'", sync = true)
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
    @Cacheable(cacheNames = ANALYTICS, key = "'daily-transactions'", sync = true)
    public List<DailyTransactionResponse> getDailyTransactions() {
        return paymentRepository.getDailyTransaction()
                .stream()
                .map(dailyTransactionProjection ->
                        new DailyTransactionResponse(
                                dailyTransactionProjection.getDate(),
                                dailyTransactionProjection.getTransactionCount()))
                .toList();
    }

    @Override
    @Cacheable(cacheNames = ANALYTICS, key = "'monthly-volume'", sync = true)
    public List<MonthlyVolumeResponse> getMonthlyPaymentVolume() {
        return paymentRepository.getMonthlyTransaction()
                .stream()
                .map(projection ->
                        new MonthlyVolumeResponse(
                                projection.getYear(),
                                projection.getMonth(),
                                projection.getTotalVolume()))
                .toList();

    }

    @Override
    @Cacheable(cacheNames = ANALYTICS, key = "'payment-status'", sync = true)
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
    @Cacheable(cacheNames = ANALYTICS, key = "'top-receivers'", sync = true)
    public List<TopReceiverResponse> getTopReceivers() {
        List<TopReceiverProjection> topReceivers = paymentRepository.getTopReceivers();
        return topReceivers
                .stream()
                .map(value -> new TopReceiverResponse(
                        value.getReceiverId(),
                        value.getTotalReceived(),
                        value.getTransactionCount()
                ))
                .toList();
    }

    @Override
    @Cacheable(cacheNames = ANALYTICS, key = "'average-amount'", sync = true)
    public AverageAmountResponse getAverageTransactionAmount() {
        AverageAmountProjection averageTransaction = paymentRepository.getAverageTransaction();
        return new AverageAmountResponse(averageTransaction.getAverageAmount());
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardAnalyticsResponse getDashboardAnalytics() {
        return DashboardAnalyticsResponse.builder()
                .overview(getOverviewAnalytics())
                .averageTransaction(getAverageTransactionAmount())
                .monthlyVolume(getMonthlyPaymentVolume())
                .dailyTransactions(getDailyTransactions())
                .paymentStatusDistribution(getPaymentStatusDistribution())
                .topReceivers(getTopReceivers())
                .build();
    }
}
