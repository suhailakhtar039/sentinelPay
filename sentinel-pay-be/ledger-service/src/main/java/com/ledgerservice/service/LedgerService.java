package com.ledgerservice.service;

import com.ledgerservice.dto.LedgerResponse;
import com.sentinelpay.common.event.PaymentCompletedEvent;

import java.util.List;

public interface LedgerService {
    void createEntry(PaymentCompletedEvent event);
    List<LedgerResponse> getMyTransactions(Long userId);
}
