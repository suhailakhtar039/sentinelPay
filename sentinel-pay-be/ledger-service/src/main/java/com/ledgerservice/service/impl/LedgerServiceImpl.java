package com.ledgerservice.service.impl;

import com.ledgerservice.entity.LedgerEntry;
import com.ledgerservice.entity.enums.LedgerStatus;
import com.ledgerservice.repository.LedgerEntryRepository;
import com.ledgerservice.service.LedgerService;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LedgerServiceImpl implements LedgerService {

    private final LedgerEntryRepository repository;

    @Override
    public void createEntry(PaymentCompletedEvent event) {
        LedgerEntry entry = LedgerEntry.builder()
                .paymentId(event.getPaymentId())
                .senderUserId(event.getSenderUserId())
                .receiverUserId(event.getReceiverUserId())
                .amount(event.getAmount())
                .currency("INR")
                .status(LedgerStatus.SUCCESS)
                .remarks("Payment Completed")
                .build();
        repository.save(entry);
    }
}
