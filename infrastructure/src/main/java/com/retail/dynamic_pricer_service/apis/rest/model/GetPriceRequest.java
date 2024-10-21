package com.retail.dynamic_pricer_service.apis.rest.model;

import com.retail.dynamic_pricer_service.exceptions.ValidationException;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class GetPriceRequest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    @NotNull(message = "Product ID cannot be null")
    private final UUID productId;
    @NotNull(message = "Brand ID cannot be null")
    private final UUID brandId;
    @NotNull(message = "Application date cannot be null")
    private final LocalDateTime applicationDate;

    public GetPriceRequest(UUID productId, UUID brandId, String applicationDate) {
        this.productId = productId;
        this.brandId = brandId;
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