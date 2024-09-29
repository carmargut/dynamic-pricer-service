package com.retail.dynamic_pricer_service.infrastructure.apis.rest;

import com.retail.dynamic_pricer_service.application.get_price.GetPriceRequest;
import com.retail.dynamic_pricer_service.application.get_price.GetPriceResponse;
import com.retail.dynamic_pricer_service.application.get_price.GetPriceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(applicationDate, formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(new RestErrorResponse("Invalid date format", "400"));
        }
        GetPriceRequest getPriceRequest = new GetPriceRequest(dateTime, brandId, productId);
        GetPriceResponse response = getPriceUseCase.execute(getPriceRequest);
        return ResponseEntity.ok(response);
    }
}

