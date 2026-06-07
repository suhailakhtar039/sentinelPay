package com.paymentservice.service;

import com.paymentservice.dto.PaymentRequest;
import com.paymentservice.dto.PaymentResponse;
import com.paymentservice.entity.Payment;
import com.paymentservice.enums.PaymentStatus;
import com.paymentservice.kafka.PaymentEventProducer;
import com.paymentservice.repository.PaymentRepository;
import com.sentinelpay.common.event.PaymentInitiatedEvent;
import com.sentinelpay.common.exception.AccessDeniedException;
import com.sentinelpay.common.exception.BadRequestException;
import com.sentinelpay.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    public PaymentResponse initiatePayment(Long senderUserId, PaymentRequest request) {
        // validation

        if (senderUserId.equals(request.getReceiverUserId())) {
            throw new BadRequestException(
                    "Sender and receiver cannot be same");
        }

        if (request.getAmount() == null ||
                request.getAmount().signum() <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }

        Payment payment = Payment.builder()
                .senderUserId(senderUserId)
                .receiverUserId(request.getReceiverUserId())
                .amount(request.getAmount())
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        PaymentInitiatedEvent event =
                PaymentInitiatedEvent.builder()
                        .paymentId(savedPayment.getPaymentId())
                        .senderUserId(savedPayment.getSenderUserId())
                        .receiverUserId(savedPayment.getReceiverUserId())
                        .amount(savedPayment.getAmount())
                        .build();

        paymentEventProducer.publishPaymentInitiated(event);

        return mapToResponse(savedPayment);

    }

    public PaymentResponse getPayment(Long paymentId, Long userId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment with id " + paymentId + " is not present"));

        boolean isOwner =
                payment.getSenderUserId().equals(userId)
                        || payment.getReceiverUserId().equals(userId);

        System.out.println("Is Owner = " + isOwner);

        if (!isOwner) {
            throw new AccessDeniedException(
                    "You are not authorized to view this payment");
        }

        return mapToResponse(payment);
    }

    public List<PaymentResponse> getMyPayments(Long userId){
        return paymentRepository
                .findBySenderUserIdOrReceiverUserId(userId, userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponse mapToResponse(Payment payment){
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .senderUserId(payment.getSenderUserId())
                .receiverUserId(payment.getReceiverUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .failureReason(payment.getFailureReason())
                .createdAt(payment.getCreatedAt())
                .build();
    }

}
