package com.authservice.client;

import com.authservice.dto.wallet.CreateWalletRequest;
import com.authservice.dto.wallet.WalletResponse;
import com.authservice.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class WalletClient {
    private final RestClient restClient;

    public ApiResponse<WalletResponse> createWallet(Long userId) {
        CreateWalletRequest request = CreateWalletRequest.builder()
                .userId(userId)
                .build();
        try {
            return restClient.post()
                    .uri("http://localhost:8082/api/wallets")
                    .body(request)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<WalletResponse>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Wallet service unavailable");
        }
    }
}
