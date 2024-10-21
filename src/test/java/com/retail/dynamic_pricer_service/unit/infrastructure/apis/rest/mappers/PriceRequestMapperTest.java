package com.retail.dynamic_pricer_service.unit.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model.GetPriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceRequestMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceRequestMapperTest {

    @Test
    void shouldMapGetPriceRequestToPriceRequest() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        String applicationDate = "2024-09-01T10:00:00";

        GetPriceRequest getPriceRequest = new GetPriceRequest(productId, brandId, applicationDate);

        PriceRequest priceRequest = PriceRequestMapper.INSTANCE.toDomain(getPriceRequest);

        assertEquals(productId, priceRequest.getProductId());
        assertEquals(brandId, priceRequest.getBrandId());
        assertEquals(LocalDateTime.parse(applicationDate), priceRequest.getApplicationDate());
    }

}