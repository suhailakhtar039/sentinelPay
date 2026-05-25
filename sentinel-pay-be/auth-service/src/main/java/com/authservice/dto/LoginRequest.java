package com.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "Enter proper email")
    private String email;

    @NotBlank(message = "Enter password")
    private String password;
}
