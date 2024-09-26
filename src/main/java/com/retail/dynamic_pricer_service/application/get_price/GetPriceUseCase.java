package com.retail.dynamic_pricer_service.application.get_price;


import com.retail.dynamic_pricer_service.application.shared.UseCase;
import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.domain.services.PriceDomainService;
import org.springframework.stereotype.Service;

@Service
public class GetPriceUseCase implements UseCase<GetPriceRequest, GetPriceResponse> {

    private final PriceDomainService priceService;

    public GetPriceUseCase(PriceDomainService priceService) {
        this.priceService = priceService;
    }

    @Override
    public GetPriceResponse execute(GetPriceRequest request) {
        Price price = priceService.findHighestPriorityPrice(
                request.productId(),
                request.brandId(),
                request.applicationDate()
        );
        return new GetPriceResponse(
                price.priceId(),
                price.brandId(),
                price.productId(),
                price.startDate(),
                price.endDate(),
                price.price()
        );
    }
}
