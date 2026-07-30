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
import java.util.stream.Collectors;

import static com.paymentservice.config.CacheNames.ANALYTICS;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final PaymentRepository paymentRepository;

    @Override
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'overview:' + #userId",
            sync = true
    )
    public OverviewAnalyticsResponse getOverviewAnalytics(Long userId) {

        OverviewAnalyticsProjection projection =
                paymentRepository.getOverviewAnalytics(userId);

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
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'daily-transactions:' + #userId",
            sync = true
    )
    public List<DailyTransactionResponse> getDailyTransactions(Long userId) {

        return paymentRepository.getDailyTransaction(userId)
                .stream()
                .map(projection ->
                        new DailyTransactionResponse(
                                projection.getDate(),
                                projection.getTransactionCount()
                        ))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'monthly-volume:' + #userId",
            sync = true
    )
    public List<MonthlyVolumeResponse> getMonthlyPaymentVolume(Long userId) {

        return paymentRepository.getMonthlyTransaction(userId)
                .stream()
                .map(projection ->
                        new MonthlyVolumeResponse(
                                projection.getYear(),
                                projection.getMonth(),
                                projection.getTotalVolume()
                        ))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'payment-status:' + #userId",
            sync = true
    )
    public List<PaymentStatusResponse> getPaymentStatusDistribution(Long userId) {

        return paymentRepository.getPaymentStatusDistribution(userId)
                .stream()
                .map(projection ->
                        new PaymentStatusResponse(
                                projection.getStatus(),
                                projection.getCount()
                        ))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'top-receivers:' + #userId",
            sync = true
    )
    public List<TopReceiverResponse> getTopReceivers(Long userId) {

        List<TopReceiverProjection> topReceivers =
                paymentRepository.getTopReceivers(userId);

        return topReceivers.stream()
                .map(value ->
                        new TopReceiverResponse(
                                value.getReceiverId(),
                                value.getTotalReceived(),
                                value.getTransactionCount()
                        ))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'average-amount:' + #userId",
            sync = true
    )
    public AverageAmountResponse getAverageTransactionAmount(Long userId) {

        AverageAmountProjection projection =
                paymentRepository.getAverageTransaction(userId);

        return new AverageAmountResponse(
                projection.getAverageAmount()
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            cacheNames = ANALYTICS,
            key = "'dashboard:' + #userId",
            sync = true
    )
    public DashboardAnalyticsResponse getDashboardAnalytics(Long userId) {

        return DashboardAnalyticsResponse.builder()
                .overview(getOverviewAnalytics(userId))
                .averageTransaction(getAverageTransactionAmount(userId))
                .monthlyVolume(getMonthlyPaymentVolume(userId))
                .dailyTransactions(getDailyTransactions(userId))
                .paymentStatusDistribution(getPaymentStatusDistribution(userId))
                .topReceivers(getTopReceivers(userId))
                .build();
    }
}