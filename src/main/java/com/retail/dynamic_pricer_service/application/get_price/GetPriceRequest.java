package com.retail.dynamic_pricer_service.application.get_price;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetPriceRequest(LocalDateTime applicationDate, UUID brandId, UUID productId) {
}