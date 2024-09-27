package com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.mappers;

import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model.PricePersistence;

public class PriceMapper {
    public static Price toDomain(PricePersistence pricePersistence) {
        return new Price(
                pricePersistence.getPriceId(),
                pricePersistence.getBrandId(),
                pricePersistence.getProductId(),
                pricePersistence.getStartDate(),
                pricePersistence.getEndDate(),
                pricePersistence.getPriority(),
                pricePersistence.getPrice(),
                pricePersistence.getCurrency()
        );
    }
}