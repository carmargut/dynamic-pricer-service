package com.retail.dynamic_pricer_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PriceRequest {

    private final UUID productId;
    private final UUID brandId;
    private final LocalDateTime applicationDate;

    public PriceRequest(UUID productId, UUID brandId, LocalDateTime applicationDate) {
        this.productId = productId;
        this.brandId = brandId;
        this.applicationDate = applicationDate;
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