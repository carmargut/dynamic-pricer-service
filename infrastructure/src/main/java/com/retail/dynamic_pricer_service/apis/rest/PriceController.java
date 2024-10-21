package com.retail.dynamic_pricer_service.apis.rest;

import com.retail.dynamic_pricer_service.apis.rest.mappers.PriceMapper;
import com.retail.dynamic_pricer_service.apis.rest.mappers.PriceRequestMapper;
import com.retail.dynamic_pricer_service.apis.rest.model.GetPriceRequest;
import com.retail.dynamic_pricer_service.apis.rest.model.GetPriceResponse;
import com.retail.dynamic_pricer_service.get_price.GetPriceUseCase;
import com.retail.dynamic_pricer_service.model.PriceRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;
    private final PriceRequestMapper priceRequestMapper;

    public PriceController(GetPriceUseCase getPriceUseCase, PriceMapper priceMapper, PriceRequestMapper priceRequestMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceMapper = priceMapper;
        this.priceRequestMapper = priceRequestMapper;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(@Valid GetPriceRequest request) {
        PriceRequest priceRequest = priceRequestMapper.toDomain(request);
        var price = getPriceUseCase.execute(priceRequest);
        GetPriceResponse response = priceMapper.toApi(price);
        return ResponseEntity.ok(response);
    }

}

