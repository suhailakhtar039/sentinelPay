package com.fraudservice.kafka;

import com.sentinelpay.common.event.FraudApprovedEvent;
import com.sentinelpay.common.event.FraudRejectedEvent;
import com.sentinelpay.common.event.PaymentFailedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishApproval(FraudApprovedEvent event){
        kafkaTemplate.send(KafkaTopics.FRAUD_APPROVED, event);
    }

    public void publishRejected(FraudRejectedEvent event){
        kafkaTemplate.send(KafkaTopics.FRAUD_REJECTED, event);
    }

}
