package com.retail.dynamic_pricer_service.get_price;

import com.retail.dynamic_pricer_service.model.Price;
import com.retail.dynamic_pricer_service.model.PriceRequest;
import com.retail.dynamic_pricer_service.services.PriceDomainService;
import org.springframework.stereotype.Service;

@Service
public class GetPriceUseCase {

    private final PriceDomainService priceService;

    public GetPriceUseCase(PriceDomainService priceService) {
        this.priceService = priceService;
    }

    public Price execute(PriceRequest request) {
        return priceService.findHighestPriorityPrice(
                request.getProductId(),
                request.getBrandId(),
                request.getApplicationDate()
        );
    }

}