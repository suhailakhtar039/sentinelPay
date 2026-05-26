package com.walletservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletResponse {
    private Long walletId;
    private Long userId;
    private BigDecimal balance;
    private String currency;
    private String status;
}
