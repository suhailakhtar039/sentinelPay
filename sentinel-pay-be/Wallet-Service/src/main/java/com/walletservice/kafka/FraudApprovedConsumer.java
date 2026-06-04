package com.walletservice.kafka;

import com.sentinelpay.common.event.FraudApprovedEvent;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.event.PaymentFailedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import com.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FraudApprovedConsumer {
    private final WalletService walletService;
    private final WalletEventProducer walletEventProducer;

    @KafkaListener(topics = KafkaTopics.FRAUD_APPROVED, groupId = "wallet-group")
    public void consume(FraudApprovedEvent event) {

        try {
            walletService.debit(event.getSenderUserId(), event.getAmount());

            walletService.credit(event.getReceiverUserId(), event.getAmount());

            walletEventProducer.publishPaymentCompleted(PaymentCompletedEvent.builder()
                    .paymentId(event.getPaymentId())
                    .senderUserId(event.getSenderUserId())
                    .receiverUserId(event.getReceiverUserId())
                    .build()
            );
        } catch (Exception e){
            walletEventProducer.publishPaymentFailed(PaymentFailedEvent.builder()
                    .paymentId(event.getPaymentId())
                    .reason(e.getMessage())
                    .build()
            );
        }
    }
}