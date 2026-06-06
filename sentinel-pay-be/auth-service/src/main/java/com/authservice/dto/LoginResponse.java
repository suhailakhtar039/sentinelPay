package com.authservice.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String email;
    private Long userId;
    private String role;
}
