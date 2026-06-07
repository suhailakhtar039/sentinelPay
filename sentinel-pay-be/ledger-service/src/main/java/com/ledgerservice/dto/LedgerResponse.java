package com.ledgerservice.dto;

import com.ledgerservice.entity.enums.LedgerStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class LedgerResponse {

    private Long ledgerId;

    private Long paymentId;

    private Long senderUserId;

    private Long receiverUserId;

    private BigDecimal amount;

    private String currency;

    private LedgerStatus status;

    private String remarks;

    private LocalDateTime transactionTime;
}