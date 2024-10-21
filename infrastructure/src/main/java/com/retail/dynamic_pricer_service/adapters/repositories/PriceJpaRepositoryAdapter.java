package com.retail.dynamic_pricer_service.adapters.repositories;

import com.retail.dynamic_pricer_service.adapters.repositories.mappers.PriceMapper;
import com.retail.dynamic_pricer_service.adapters.repositories.model.PricePersistence;
import com.retail.dynamic_pricer_service.model.Price;
import com.retail.dynamic_pricer_service.ports.PriceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PriceJpaRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;

    public PriceJpaRepositoryAdapter(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public List<Price> findByProductAndBrand(UUID productId, UUID brandId) {
        List<PricePersistence> pricePersistences = priceJpaRepository.findByProductIdAndBrandId(productId, brandId);

        return pricePersistences.stream()
                .map(PriceMapper::toDomain)
                .collect(Collectors.toList());
    }

}
