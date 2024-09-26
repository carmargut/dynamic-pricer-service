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
        pricePersistence.setPriceId(price.priceId());
        pricePersistence.setBrandId(price.brandId());
        pricePersistence.setProductId(price.productId());
        pricePersistence.setStartDate(price.startDate());
        pricePersistence.setEndDate(price.endDate());
        pricePersistence.setPriority(price.priority());
        pricePersistence.setPrice(price.price());
        pricePersistence.setCurrency(price.currency());
        return pricePersistence;
    }
}