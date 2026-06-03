package com.paymentservice.kafka;

import com.paymentservice.entity.Payment;
import com.paymentservice.enums.PaymentStatus;
import com.paymentservice.repository.PaymentRepository;
import com.sentinelpay.common.event.PaymentFailedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentFailedConsumer {

    private final PaymentRepository repository;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_FAILED,
            groupId = "payment-group"
    )
    public void consume(
            PaymentFailedEvent event) {

        Payment payment =
                repository.findById(
                        event.getPaymentId()
                ).orElseThrow();

        payment.setStatus(
                PaymentStatus.FAILED
        );

        repository.save(payment);

        log.info(
                "Payment {} failed. Reason={}",
                event.getPaymentId(),
                event.getReason()
        );
    }
}