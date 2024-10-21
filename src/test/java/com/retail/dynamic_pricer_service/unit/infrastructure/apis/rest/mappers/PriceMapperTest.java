package com.retail.dynamic_pricer_service.unit.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model.GetPriceResponse;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceMapperTest {

    @Test
    void shouldMapPriceToGetPriceResponse() {
        UUID priceId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 1, 23, 59, 59);
        BigDecimal priceValue = new BigDecimal("35.00");
        String currency = "EUR";

        Price price = new Price(priceId, brandId, productId, startDate, endDate, 1, priceValue, currency);

        GetPriceResponse response = PriceMapper.INSTANCE.toApi(price);

        assertEquals(priceId, response.priceId());
        assertEquals(brandId, response.brandId());
        assertEquals(productId, response.productId());
        assertEquals(startDate, response.startDate());
        assertEquals(endDate, response.endDate());
        assertEquals(priceValue, response.price());
        assertEquals(currency, response.currency());
    }

}