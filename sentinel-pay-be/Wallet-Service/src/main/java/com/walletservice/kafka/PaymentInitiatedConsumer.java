package com.walletservice.kafka;

import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.event.PaymentFailedEvent;
import com.sentinelpay.common.event.PaymentInitiatedEvent;
import com.sentinelpay.common.exception.BadRequestException;
import com.sentinelpay.common.kafka.KafkaTopics;
import com.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentInitiatedConsumer {

    private final WalletService walletService;
    private final WalletEventProducer walletEventProducer;

    @KafkaListener(topics = KafkaTopics.PAYMENT_INITIATED, groupId = "wallet-group")
    public void consume(PaymentInitiatedEvent event) {
        try {
            log.info("Received payment event:{}", event.getPaymentId());
            // Debit service
            walletService.debit(event.getSenderUserId(), event.getAmount());

            // Credit service
            walletService.credit(event.getReceiverUserId(), event.getAmount());

            // Wallet event producer baad me aaya payment complete batane ke liye
            walletEventProducer.publishPaymentCompleted(PaymentCompletedEvent.builder()
                    .paymentId(event.getPaymentId())
                    .senderUserId(event.getSenderUserId())
                    .receiverUserId(event.getReceiverUserId())
                    .build()
            );
        } catch (BadRequestException e) {
            log.info("Payment id:{}", event.getPaymentId());
            walletEventProducer.publishPaymentFailed(PaymentFailedEvent.builder()
                    .paymentId(event.getPaymentId())
                    .reason(e.getMessage())
                    .build()
            );
        }
    }

}
