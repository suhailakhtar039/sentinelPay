package com.paymentservice.controller;

import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.DashboardAnalyticsResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;
import com.paymentservice.service.AnalyticsService;
import com.sentinelpay.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/overview")
    public ApiResponse<OverviewAnalyticsResponse> getOverviewAnalytics() {
        return ApiResponse.<OverviewAnalyticsResponse>builder()
                .success(true)
                .message("Overview Analytics fetched successfully")
                .data(analyticsService.getOverviewAnalytics())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/payment-status")
    public ApiResponse<List<PaymentStatusResponse>> getPaymentStatusDistribution() {
        return ApiResponse.<List<PaymentStatusResponse>>builder()
                .success(true)
                .message("Payment status distribution fetched successfully")
                .data(analyticsService.getPaymentStatusDistribution())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/average-amount")
    public ApiResponse<AverageAmountResponse> getAverageTransactionAmount() {
        return ApiResponse.<AverageAmountResponse>builder()
                .success(true)
                .message("Average transaction amount fetched successfully")
                .data(analyticsService.getAverageTransactionAmount())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/top-receivers")
    public ApiResponse<List<TopReceiverResponse>> getTopReceivers() {

        return ApiResponse.<List<TopReceiverResponse>>builder()
                .success(true)
                .message("Top receivers fetched successfully")
                .data(analyticsService.getTopReceivers())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/daily-transactions")
    public ApiResponse<List<DailyTransactionResponse>> getDailyTransactions() {
        return ApiResponse.<List<DailyTransactionResponse>>builder()
                .success(true)
                .message("Daily transactions fetched successfully")
                .data(analyticsService.getDailyTransactions())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/monthly-volume")
    public ApiResponse<List<MonthlyVolumeResponse>> getMonthlyVolume() {

        return ApiResponse.<List<MonthlyVolumeResponse>>builder()
                .success(true)
                .message("Monthly payment volume fetched successfully")
                .data(analyticsService.getMonthlyPaymentVolume())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardAnalyticsResponse> getDashboardAnalytics() {
        return ResponseEntity.ok(analyticsService.getDashboardAnalytics());
    }

}
