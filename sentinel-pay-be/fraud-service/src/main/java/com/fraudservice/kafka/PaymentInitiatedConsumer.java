package com.fraudservice.kafka;

import com.fraudservice.dto.FraudDecision;
import com.fraudservice.service.FraudDetectionService;
import com.sentinelpay.common.event.FraudApprovedEvent;
import com.sentinelpay.common.event.FraudRejectedEvent;
import com.sentinelpay.common.event.PaymentInitiatedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentInitiatedConsumer {
    private final FraudDetectionService fraudDetectionService;
    private final FraudEventProducer fraudEventProducer;

    @KafkaListener(topics = KafkaTopics.PAYMENT_INITIATED, groupId = "fraud-service")
    public void consume(PaymentInitiatedEvent event) {
        FraudDecision fraudulent = fraudDetectionService.evaluate(event);
        if (fraudulent.isFraudulent()) {
            FraudRejectedEvent rejectedEvent = FraudRejectedEvent.builder()
                    .paymentId(event.getPaymentId())
                    .reason(fraudulent.getReason())
                    .build();

            fraudEventProducer.publishRejected(rejectedEvent);
            log.info(
                    "Payment {} rejected by fraud service",
                    event.getPaymentId()
            );
        } else {
            FraudApprovedEvent approvedEvent = FraudApprovedEvent.builder()
                    .paymentId(event.getPaymentId())
                    .senderUserId(event.getSenderUserId())
                    .receiverUserId(event.getReceiverUserId())
                    .amount(event.getAmount())
                    .build();
            fraudEventProducer.publishApproval(approvedEvent);
            log.info(
                    "Payment {} approved by fraud service",
                    event.getPaymentId()
            );
        }
    }

}
