package com.authservice.controller;

import com.authservice.dto.ChangePasswordRequest;
import com.authservice.dto.UpdateProfileRequest;
import com.authservice.dto.UserProfileResponse;
import com.authservice.service.UserService;
import com.sentinelpay.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .success(true)
                        .data(service.getMyProfile(userId))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<String>> updateProfile(
            @RequestHeader("X-User-Id") Long id,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        service.updateProfile(id, request);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Profile fetched successfully")
                        .data("Name updated successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestHeader("X-User-Id") Long id,
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest
    ) {
        service.changePassword(id, changePasswordRequest);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Password changed successfully")
                        .data("Password Updated successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
