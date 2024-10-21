package com.retail.dynamic_pricer_service.integration.infrastructure.apis.rest;

import com.retail.dynamic_pricer_service.application.get_price.GetPriceUseCase;
import com.retail.dynamic_pricer_service.domain.exceptions.DomainException;
import com.retail.dynamic_pricer_service.domain.exceptions.EntityNotFoundException;
import com.retail.dynamic_pricer_service.domain.exceptions.ValidationException;
import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPriceUseCase getPriceUseCase;

    @Test
    void shouldReturnPriceWhenRequestIsValid() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        UUID priceId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        BigDecimal price = BigDecimal.valueOf(25.45);
        String applicationDate = "2020-06-14T10:00:00";
        String endDate = "2020-06-14T18:30:00";
        LocalDateTime startDateTime = LocalDateTime.parse(applicationDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
        Price mockPrice = new Price(priceId, brandId, productId, startDateTime, endDateTime, 1, price, "EUR");
        Mockito.when(getPriceUseCase.execute(any(PriceRequest.class))).thenReturn(mockPrice);

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.brandId").value(brandId.toString()))
                .andExpect(jsonPath("$.startDate").value(applicationDate))
                .andExpect(jsonPath("$.endDate").value(endDate));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidDateIsProvided() throws Exception {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "invalid-date")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenPriceNotAvailable() throws Exception {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        String applicationDate = "2020-06-14T10:00:00";
        Mockito.when(getPriceUseCase.execute(any(PriceRequest.class))).thenThrow(new EntityNotFoundException("No applicable price found for the given parameters"));

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No applicable price found for the given parameters"));
    }

    @Test
    void shouldReturnBadRequestWhenValidationExceptionIsThrown() throws Exception {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        String applicationDate = "2020-06-14T10:00:00";

        Mockito.when(getPriceUseCase.execute(any(PriceRequest.class)))
                .thenThrow(new ValidationException("Validation error"));

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"));
    }

    @Test
    void shouldReturnInternalServerErrorWhenDomainExceptionIsThrown() throws Exception {
        UUID productId = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        String applicationDate = "2020-06-14T10:00:00";

        Mockito.when(getPriceUseCase.execute(any(PriceRequest.class)))
                .thenThrow(new DomainException("Unexpected error"));

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unexpected error"))
                .andExpect(jsonPath("$.status").value("500"));
    }

    @Test
    void shouldReturnBadRequestWhenProductIdIsInvalid() throws Exception {
        String invalidProductId = "invalid-uuid";
        UUID brandId = UUID.randomUUID();
        String applicationDate = "2020-06-14T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", invalidProductId)
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBrandIdIsInvalid() throws Exception {
        UUID productId = UUID.randomUUID();
        String invalidBrandId = "invalid-uuid";
        String applicationDate = "2020-06-14T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", invalidBrandId)
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenBothProductIdAndBrandIdAreInvalid() throws Exception {
        String invalidProductId = "invalid-uuid";
        String invalidBrandId = "invalid-uuid";
        String applicationDate = "2020-06-14T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", invalidProductId)
                        .param("brandId", invalidBrandId)
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}