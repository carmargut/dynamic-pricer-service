package com.retail.dynamic_pricer_service.domain.model;

import com.retail.dynamic_pricer_service.domain.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Price(UUID priceId, UUID brandId, UUID productId, LocalDateTime startDate, LocalDateTime endDate,
                    int priority, BigDecimal price, String currency) {
    public Price(UUID priceId,
                 UUID brandId,
                 UUID productId,
                 LocalDateTime startDate,
                 LocalDateTime endDate,
                 int priority,
                 BigDecimal price,
                 String currency) {

        this.priceId = requireNonNull(priceId, "Price ID cannot be null");
        this.brandId = requireNonNull(brandId, "Brand ID cannot be null");
        this.productId = requireNonNull(productId, "Product ID cannot be null");
        this.startDate = requireNonNull(startDate, "Start date cannot be null");
        this.endDate = requireNonNull(endDate, "End date cannot be null");
        this.priority = priority;
        this.price = requireNonNull(price, "Price cannot be null");
        this.currency = requireNonNull(currency, "Currency cannot be null");

        if (endDate.isBefore(startDate)) {
            throw new ValidationException("End date cannot be before start date");
        }
    }

    private static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new ValidationException(message);
        }
        return obj;
    }
}