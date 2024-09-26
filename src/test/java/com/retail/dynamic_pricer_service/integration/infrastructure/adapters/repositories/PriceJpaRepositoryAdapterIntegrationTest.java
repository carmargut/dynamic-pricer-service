package com.retail.dynamic_pricer_service.integration.infrastructure.adapters.repositories;

import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.PriceJpaRepository;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.PriceJpaRepositoryAdapter;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model.PricePersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PriceJpaRepositoryAdapterIntegrationTest {

    @Autowired
    private PriceJpaRepositoryAdapter priceRepositoryAdapter;

    @Autowired
    private PriceJpaRepository priceJpaRepository;

    @Test
    void shouldFindPricesByProductAndBrand() {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        PricePersistence pricePersistence = new PricePersistence();
        pricePersistence.setPriceId(UUID.randomUUID());
        pricePersistence.setProductId(productId);
        pricePersistence.setBrandId(brandId);
        pricePersistence.setStartDate(LocalDateTime.now());
        pricePersistence.setEndDate(LocalDateTime.now().plusMonths(1));
        pricePersistence.setPriority(1);
        pricePersistence.setPrice(new BigDecimal("100.00"));
        pricePersistence.setCurrency("EUR");
        priceJpaRepository.save(pricePersistence);

        List<Price> prices = priceRepositoryAdapter.findByProductAndBrand(productId, brandId);

        assertFalse(prices.isEmpty());
        Price retrievedPrice = prices.get(0);
        assertEquals(productId, retrievedPrice.getProductId());
        assertEquals(brandId, retrievedPrice.getBrandId());
        assertEquals(new BigDecimal("100.00"), retrievedPrice.getPrice());
    }

    @Test
    void shouldReturnEmptyListWhenNoPricesExist() {
        UUID nonExistentProductId = UUID.randomUUID();
        UUID nonExistentBrandId = UUID.randomUUID();

        List<Price> prices = priceRepositoryAdapter.findByProductAndBrand(nonExistentProductId, nonExistentBrandId);

        assertTrue(prices.isEmpty());
    }
}