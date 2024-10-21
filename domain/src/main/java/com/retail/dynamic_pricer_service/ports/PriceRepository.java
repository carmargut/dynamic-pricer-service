package com.retail.dynamic_pricer_service.ports;

import com.retail.dynamic_pricer_service.model.Price;

import java.util.List;
import java.util.UUID;

public interface PriceRepository {

    List<Price> findByProductAndBrand(UUID productId, UUID brandId);

}


