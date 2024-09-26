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

    public static PricePersistence toPersistence(Price price) {
        PricePersistence pricePersistence = new PricePersistence();
        pricePersistence.setPriceId(price.getPriceId());
        pricePersistence.setBrandId(price.getBrandId());
        pricePersistence.setProductId(price.getProductId());
        pricePersistence.setStartDate(price.getStartDate());
        pricePersistence.setEndDate(price.getEndDate());
        pricePersistence.setPriority(price.getPriority());
        pricePersistence.setPrice(price.getPrice());
        pricePersistence.setCurrency(price.getCurrency());
        return pricePersistence;
    }
}