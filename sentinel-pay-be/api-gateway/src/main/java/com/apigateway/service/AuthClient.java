package com.apigateway.service;

import com.sentinelpay.common.dto.TokenValidationRequest;
import com.sentinelpay.common.dto.TokenValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthClient {

    private final WebClient webClient;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public Mono<TokenValidationResponse> validateToken(
            String token
    ) {

        return webClient.post()
                .uri(authServiceUrl + "/api/auth/validate")
                .bodyValue(
                        new TokenValidationRequest(token)
                )
                .retrieve()
                .bodyToMono(
                        TokenValidationResponse.class
                );
    }
}