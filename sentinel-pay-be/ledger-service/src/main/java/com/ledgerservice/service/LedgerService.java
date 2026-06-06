package com.ledgerservice.service;

import com.sentinelpay.common.event.PaymentCompletedEvent;

public interface LedgerService {
    void createEntry(PaymentCompletedEvent event);
}
