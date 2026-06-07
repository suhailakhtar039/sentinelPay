package com.ledgerservice.service.impl;

import com.ledgerservice.dto.LedgerResponse;
import com.ledgerservice.entity.LedgerEntry;
import com.ledgerservice.entity.enums.LedgerStatus;
import com.ledgerservice.repository.LedgerEntryRepository;
import com.ledgerservice.service.LedgerService;
import com.sentinelpay.common.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<LedgerResponse> getMyTransactions(Long userId) {

        return repository
                .findBySenderUserIdOrReceiverUserId(
                        userId,
                        userId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private LedgerResponse mapToResponse(
            LedgerEntry entry
    ) {
        return LedgerResponse.builder()
                .ledgerId(entry.getEntryId())
                .paymentId(entry.getPaymentId())
                .senderUserId(entry.getSenderUserId())
                .receiverUserId(entry.getReceiverUserId())
                .amount(entry.getAmount())
                .currency(entry.getCurrency())
                .status(entry.getStatus())
                .remarks(entry.getRemarks())
                .transactionTime(entry.getTransactionTime())
                .build();
    }
}
