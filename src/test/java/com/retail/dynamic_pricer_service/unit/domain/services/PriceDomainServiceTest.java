package com.retail.dynamic_pricer_service.unit.domain.services;

import com.retail.dynamic_pricer_service.domain.exceptions.EntityNotFoundException;
import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.domain.ports.PriceRepository;
import com.retail.dynamic_pricer_service.domain.services.PriceDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PriceDomainServiceTest {

    private PriceRepository priceRepository;
    private PriceDomainService priceDomainService;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceDomainService = new PriceDomainService(priceRepository);
    }

    @Test
    void shouldReturnHighestPriorityPriceWhenPricesFound() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        LocalDateTime applicationDate = LocalDateTime.of(2024, 9, 1, 10, 0, 0);

        Price lowerPriorityPrice = new Price(
                UUID.randomUUID(), brandId, productId,
                LocalDateTime.of(2024, 9, 1, 0, 0, 0),
                LocalDateTime.of(2024, 9, 1, 23, 59, 59),
                0, new BigDecimal("30.00"), "EUR"
        );

        Price higherPriorityPrice = new Price(
                UUID.randomUUID(), brandId, productId,
                LocalDateTime.of(2024, 9, 1, 0, 0, 0),
                LocalDateTime.of(2024, 9, 1, 23, 59, 59),
                1, new BigDecimal("35.00"), "EUR"
        );
        List<Price> priceList = new ArrayList<>();
        priceList.add(lowerPriorityPrice);
        priceList.add(higherPriorityPrice);
        when(priceRepository.findByProductAndBrand(productId, brandId)).thenReturn(priceList);

        Price result = priceDomainService.findHighestPriorityPrice(productId, brandId, applicationDate);

        assertEquals(higherPriorityPrice, result);
        verify(priceRepository, times(1)).findByProductAndBrand(productId, brandId);
    }

    @Test
    void shouldThrowExceptionWhenNoPriceFound() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        LocalDateTime applicationDate = LocalDateTime.of(2024, 9, 1, 10, 0, 0);
        when(priceRepository.findByProductAndBrand(productId, brandId)).thenReturn(new ArrayList<>());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> priceDomainService.findHighestPriorityPrice(productId, brandId, applicationDate)
        );

        assertEquals("No applicable price found for the given parameters", exception.getMessage());
        verify(priceRepository, times(1)).findByProductAndBrand(productId, brandId);
    }

    @Test
    void shouldFilterOutPricesOutsideDateRange() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        LocalDateTime applicationDate = LocalDateTime.of(2024, 9, 1, 10, 0, 0);
        Price outOfRangePrice = new Price(
                UUID.randomUUID(), brandId, productId,
                LocalDateTime.of(2023, 9, 1, 0, 0, 0),
                LocalDateTime.of(2023, 9, 1, 23, 59, 59),
                1, new BigDecimal("35.00"), "EUR"
        );
        Price validPrice = new Price(
                UUID.randomUUID(), brandId, productId,
                LocalDateTime.of(2024, 9, 1, 0, 0, 0),
                LocalDateTime.of(2024, 9, 1, 23, 59, 59),
                0, new BigDecimal("30.00"), "EUR"
        );
        List<Price> priceList = new ArrayList<>();
        priceList.add(outOfRangePrice);
        priceList.add(validPrice);
        when(priceRepository.findByProductAndBrand(productId, brandId)).thenReturn(priceList);

        Price result = priceDomainService.findHighestPriorityPrice(productId, brandId, applicationDate);

        assertEquals(validPrice, result);
        verify(priceRepository, times(1)).findByProductAndBrand(productId, brandId);
    }
}