package com.authservice.dto.wallet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateWalletRequest {

    private Long userId;
}