package com.paymentservice.controller;

import com.paymentservice.dto.analytics.OverviewAnalyticsResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.service.AnalyticsService;
import com.sentinelpay.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
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

}
