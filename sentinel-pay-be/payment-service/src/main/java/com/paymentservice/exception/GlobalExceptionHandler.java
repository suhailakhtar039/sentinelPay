package com.paymentservice.exception;

import com.sentinelpay.common.exception.BadRequestException;
import com.sentinelpay.common.exception.ErrorDto;
import com.sentinelpay.common.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequest(
            BadRequestException exception,
            HttpServletRequest request) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(exception.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
            ){
        ErrorDto response = ErrorDto.builder()
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
