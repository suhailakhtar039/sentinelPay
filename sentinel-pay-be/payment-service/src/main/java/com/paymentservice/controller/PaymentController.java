package com.paymentservice.controller;

import com.paymentservice.dto.PaymentRequest;
import com.paymentservice.dto.PaymentResponse;
import com.paymentservice.service.PaymentService;
import com.sentinelpay.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> initiatePayment(
            @RequestHeader("X-User-Id") Long senderUserId,
            @Valid @RequestBody PaymentRequest request
    ) {
        PaymentResponse response = paymentService.initiatePayment(senderUserId, request);
        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment initiated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()

        );
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long paymentId
    ) {
        PaymentResponse response = paymentService.getPayment(paymentId, userId);
        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
