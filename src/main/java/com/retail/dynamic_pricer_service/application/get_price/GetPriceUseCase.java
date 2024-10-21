package com.retail.dynamic_pricer_service.application.get_price;

import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.domain.services.PriceDomainService;
import org.springframework.stereotype.Service;

@Service
public class GetPriceUseCase {

    private final PriceDomainService priceService;

    public GetPriceUseCase(PriceDomainService priceService) {
        this.priceService = priceService;
    }

    public Price execute(PriceRequest request) {
        return priceService.findHighestPriorityPrice(
                request.productId(),
                request.brandId(),
                request.applicationDate()
        );
    }

}