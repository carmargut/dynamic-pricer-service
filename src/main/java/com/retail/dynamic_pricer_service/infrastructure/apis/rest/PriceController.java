package com.retail.dynamic_pricer_service.infrastructure.apis.rest;

import com.retail.dynamic_pricer_service.application.get_price.GetPriceUseCase;
import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model.GetPriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.adapters.repositories.model.GetPriceResponse;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceMapper;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers.PriceRequestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(@RequestParam UUID productId, @RequestParam UUID brandId, @RequestParam String applicationDate) {
        GetPriceRequest getPriceRequest = new GetPriceRequest(productId, brandId, applicationDate);
        PriceRequest priceRequest = PriceRequestMapper.INSTANCE.toDomain(getPriceRequest);
        var price = getPriceUseCase.execute(priceRequest);
        GetPriceResponse response = PriceMapper.INSTANCE.toApi(price);
        return ResponseEntity.ok(response);
    }

}

