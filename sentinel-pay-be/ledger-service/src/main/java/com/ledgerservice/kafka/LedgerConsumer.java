package com.ledgerservice.kafka;

import com.ledgerservice.service.LedgerService;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import com.sentinelpay.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LedgerConsumer {

    private final LedgerService service;

    @KafkaListener(topics = KafkaTopics.PAYMENT_COMPLETED, groupId = "ledger-service")
    public void consume(PaymentCompletedEvent event){
        service.createEntry(event);
    }
}
