package com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model;

import com.retail.dynamic_pricer_service.domain.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GetPriceRequest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final UUID productId;
    private final UUID brandId;
    private final LocalDateTime applicationDate;

    public GetPriceRequest(UUID productId, UUID brandId, String applicationDate) {
        this.productId = requireNonNull(productId, "Product ID cannot be null");
        this.brandId = requireNonNull(brandId, "Brand ID cannot be null");
        this.applicationDate = parseApplicationDate(applicationDate);
    }

    private LocalDateTime parseApplicationDate(String applicationDate) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(applicationDate, FORMATTER);
            return dateTime;
        } catch (Exception e) {
            throw new ValidationException("Invalid date format");
        }
    }

    private <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new ValidationException(message);
        }
        return obj;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getBrandId() {
        return brandId;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

}