package com.retail.dynamic_pricer_service.adapters.repositories;

import com.retail.dynamic_pricer_service.adapters.repositories.model.PricePersistence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PriceJpaRepository extends JpaRepository<PricePersistence, UUID> {

    List<PricePersistence> findByProductIdAndBrandId(UUID productId, UUID brandId);

}

