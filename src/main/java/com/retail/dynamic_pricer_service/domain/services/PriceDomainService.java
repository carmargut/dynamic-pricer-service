package com.retail.dynamic_pricer_service.domain.services;

import com.retail.dynamic_pricer_service.domain.exceptions.EntityNotFoundException;
import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.domain.ports.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class PriceDomainService {
    private final PriceRepository priceRepository;

    public PriceDomainService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price findHighestPriorityPrice(UUID productId, UUID brandId, LocalDateTime applicationDate) {
        List<Price> prices = priceRepository.findByProductAndBrand(productId, brandId);

        return prices.stream()
                .filter(price -> applicationDate.isBefore(price.endDate()) && applicationDate.isAfter(price.startDate()))
                .max(Comparator.comparingInt(Price::priority))
                .orElseThrow(() -> new EntityNotFoundException("No applicable price found for the given parameters"));
    }
}
