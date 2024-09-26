package com.retail.dynamic_pricer_service.unit.application.get_price;

import com.retail.dynamic_pricer_service.application.get_price.GetPriceRequest;
import com.retail.dynamic_pricer_service.application.get_price.GetPriceResponse;
import com.retail.dynamic_pricer_service.application.get_price.GetPriceUseCase;
import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.domain.services.PriceDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GetPriceUseCaseTest {

    private PriceDomainService priceDomainService;
    private GetPriceUseCase getPriceUseCase;

    @BeforeEach
    void setUp() {
        priceDomainService = mock(PriceDomainService.class);
        getPriceUseCase = new GetPriceUseCase(priceDomainService);
    }

    @Test
    void shouldReturnValidPriceResponseWhenPriceIsFound() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        UUID priceId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 10, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 1, 10, 0, 0);
        BigDecimal priceValue = new BigDecimal("35.50");
        Price mockPrice = new Price(priceId, brandId, productId, startDate, endDate, 1, priceValue, "EUR");
        when(priceDomainService.findHighestPriorityPrice(productId, brandId, startDate)).thenReturn(mockPrice);
        GetPriceRequest request = new GetPriceRequest(startDate, brandId, productId);

        GetPriceResponse response = getPriceUseCase.execute(request);

        assertEquals(priceId, response.priceId());
        assertEquals(brandId, response.brandId());
        assertEquals(productId, response.productId());
        assertEquals(startDate, response.startDate());
        assertEquals(endDate, response.endDate());
        assertEquals(priceValue, response.price());
        verify(priceDomainService, times(1)).findHighestPriorityPrice(productId, brandId, startDate);
    }

    @Test
    void shouldThrowExceptionWhenPriceNotFound() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 10, 0, 0);
        when(priceDomainService.findHighestPriorityPrice(productId, brandId, startDate)).thenThrow(new NoSuchElementException("Price not found"));
        GetPriceRequest request = new GetPriceRequest(startDate, brandId, productId);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> getPriceUseCase.execute(request));
        assertEquals("Price not found", exception.getMessage());
        verify(priceDomainService, times(1)).findHighestPriorityPrice(productId, brandId, startDate);
    }
}