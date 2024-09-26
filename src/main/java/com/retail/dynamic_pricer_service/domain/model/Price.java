package com.retail.dynamic_pricer_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
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

        this.priceId = Objects.requireNonNull(priceId, "Price ID cannot be null");
        this.brandId = Objects.requireNonNull(brandId, "Brand ID cannot be null");
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null");
        this.endDate = Objects.requireNonNull(endDate, "End date cannot be null");
        this.priority = priority;
        this.price = Objects.requireNonNull(price, "Price cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }
}