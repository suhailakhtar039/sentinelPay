package com.paymentservice.kafka;

import com.paymentservice.entity.Payment;
import com.paymentservice.enums.PaymentStatus;
import com.paymentservice.repository.PaymentRepository;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.exception.ResourceNotFoundException;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCompletedConsumer {

    private final PaymentRepository repository;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_COMPLETED,
            groupId = "payment-group")
    public void consume(PaymentCompletedEvent event) {
        Payment payment = repository.findById(event.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment column with id " + event.getPaymentId() + " is not present"));

        payment.setStatus(PaymentStatus.COMPLETED);

        repository.save(payment);

        log.info("Payment {} completed", payment.getPaymentId());

    }

}
