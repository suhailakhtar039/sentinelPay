package com.apigateway.controller;

import com.apigateway.service.AuthClient;
import com.sentinelpay.common.dto.TokenValidationRequest;
import com.sentinelpay.common.dto.TokenValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AuthClient authClient;

    @PostMapping("/validate")
    public Mono<TokenValidationResponse> validate(
            @RequestBody TokenValidationRequest request
    ) {

        return authClient.validateToken(
                request.getToken()
        );
    }
}