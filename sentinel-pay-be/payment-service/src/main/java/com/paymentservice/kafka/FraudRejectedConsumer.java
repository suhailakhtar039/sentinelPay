package com.paymentservice.kafka;

import com.paymentservice.entity.Payment;
import com.paymentservice.enums.PaymentStatus;
import com.paymentservice.repository.PaymentRepository;
import com.sentinelpay.common.event.FraudRejectedEvent;
import com.sentinelpay.common.exception.ResourceNotFoundException;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FraudRejectedConsumer {
    private final PaymentRepository repository;

    @KafkaListener(topics = KafkaTopics.FRAUD_REJECTED, groupId = "payment-service")
    public void consume(FraudRejectedEvent event) {
        Payment payment = repository.findById(event.getPaymentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment Id: " + event.getPaymentId() + " Not found."));

        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailureReason(event.getReason());
        repository.save(payment);
        log.info(
                "Payment {} rejected by fraud engine. Reason={}",
                event.getPaymentId(),
                event.getReason()
        );

    }
}
