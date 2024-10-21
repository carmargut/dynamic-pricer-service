package com.retail.dynamic_pricer_service.unit.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceRequestMapper;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceRequestMapperImpl;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PriceRequestMapperTest {

    @InjectMocks
    private PriceRequestMapper priceRequestMapper = new PriceRequestMapperImpl();

    @Test
    void shouldMapGetPriceRequestToPriceRequest() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        String applicationDate = "2024-09-01T10:00:00";

        GetPriceRequest getPriceRequest = new GetPriceRequest(productId, brandId, applicationDate);

        PriceRequest priceRequest = priceRequestMapper.toDomain(getPriceRequest);

        assertEquals(productId, priceRequest.productId());
        assertEquals(brandId, priceRequest.brandId());
        assertEquals(LocalDateTime.parse(applicationDate), priceRequest.applicationDate());
    }

}