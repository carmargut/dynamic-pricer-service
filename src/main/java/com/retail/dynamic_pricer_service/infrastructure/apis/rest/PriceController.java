package com.retail.dynamic_pricer_service.infrastructure.apis.rest;

import com.retail.dynamic_pricer_service.application.get_price.GetPriceUseCase;
import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceMapper;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceRequestMapper;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(@Valid GetPriceRequest request) {
        PriceRequest priceRequest = PriceRequestMapper.INSTANCE.toDomain(request);
        var price = getPriceUseCase.execute(priceRequest);
        GetPriceResponse response = PriceMapper.INSTANCE.toApi(price);
        return ResponseEntity.ok(response);
    }

}

