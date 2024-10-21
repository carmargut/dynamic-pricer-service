package com.retail.dynamic_pricer_service.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record PriceRequest(UUID productId, UUID brandId, LocalDateTime applicationDate) {

}