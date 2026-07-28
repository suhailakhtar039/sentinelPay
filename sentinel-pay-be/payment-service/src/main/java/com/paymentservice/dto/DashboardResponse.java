package com.paymentservice.dto;

import com.paymentservice.dto.analytics.AverageAmountResponse;
import com.paymentservice.dto.analytics.DailyTransactionResponse;
import com.paymentservice.dto.analytics.MonthlyVolumeResponse;
import com.paymentservice.dto.analytics.PaymentStatusResponse;
import com.paymentservice.dto.analytics.TopReceiverResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private DashboardSummary summary;

    private List<RecentPaymentResponse> recentPayments;

    private DailyTransactionResponse dailyTransactions;

    private MonthlyVolumeResponse monthlyVolume;

    private PaymentStatusResponse paymentStatus;

    private TopReceiverResponse topReceivers;

    private AverageAmountResponse averageAmount;
}
