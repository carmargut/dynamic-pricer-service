package com.retail.dynamic_pricer_service.infrastructure.apis.rest;

import java.time.LocalDateTime;

public record RestErrorResponse(String message, String status, LocalDateTime timestamp) {
    public RestErrorResponse(String message, String status) {
        this(message, status, LocalDateTime.now());
    }
}