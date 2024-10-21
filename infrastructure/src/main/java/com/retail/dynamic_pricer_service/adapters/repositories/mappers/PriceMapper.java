package com.retail.dynamic_pricer_service.adapters.repositories.mappers;

import com.retail.dynamic_pricer_service.adapters.repositories.model.PricePersistence;
import com.retail.dynamic_pricer_service.model.Price;

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