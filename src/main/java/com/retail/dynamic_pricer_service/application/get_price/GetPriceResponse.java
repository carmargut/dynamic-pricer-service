package com.retail.dynamic_pricer_service.application.get_price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetPriceResponse(
        UUID priceId,
        UUID brandId,
        UUID productId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}