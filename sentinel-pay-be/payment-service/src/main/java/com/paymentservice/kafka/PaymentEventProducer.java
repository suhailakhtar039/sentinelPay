package com.paymentservice.kafka;

import com.sentinelpay.common.event.PaymentInitiatedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentInitiated(PaymentInitiatedEvent event){
        kafkaTemplate.send(KafkaTopics.PAYMENT_INITIATED, event);
    }

}
