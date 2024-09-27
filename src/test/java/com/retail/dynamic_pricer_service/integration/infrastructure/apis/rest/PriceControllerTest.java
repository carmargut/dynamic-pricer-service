package com.retail.dynamic_pricer_service.integration.infrastructure.apis.rest;

import com.retail.dynamic_pricer_service.application.get_price.GetPriceRequest;
import com.retail.dynamic_pricer_service.application.get_price.GetPriceResponse;
import com.retail.dynamic_pricer_service.application.get_price.GetPriceUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

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
        GetPriceResponse mockResponse = new GetPriceResponse(priceId, brandId, productId, startDateTime, endDateTime, price, "EUR");
        Mockito.when(getPriceUseCase.execute(any(GetPriceRequest.class))).thenReturn(mockResponse);

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
        Mockito.when(getPriceUseCase.execute(any(GetPriceRequest.class))).thenThrow(new NoSuchElementException("Price not found"));

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Price not found"));
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