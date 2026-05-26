package com.authservice.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletResponse {

    private Long walletId;

    private Long userId;

    private BigDecimal balance;

    private String currency;

    private String status;
}