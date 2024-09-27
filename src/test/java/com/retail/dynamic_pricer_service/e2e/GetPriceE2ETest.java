package com.retail.dynamic_pricer_service.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetPriceE2ETest {

    private static final UUID productId = UUID.fromString("1e53c1f0-d3f4-11ec-9d64-000000035455");
    private static final UUID brandId = UUID.fromString("1e53c1f0-d3f4-11ec-9d64-000000000001");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1_shouldReturnPriceFor10AMOnJune14() throws Exception {
        String applicationDate = "2020-06-14T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceId").value("1e53c1f0-d3f4-11ec-9d64-000000000001"))
                .andExpect(jsonPath("$.brandId").value(brandId.toString()))
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    void test2_shouldReturnPriceFor4PMOnJune14() throws Exception {
        String applicationDate = "2020-06-14T16:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void test3_shouldReturnPriceFor9PMOnJune14() throws Exception {
        String applicationDate = "2020-06-14T21:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void test4_shouldReturnPriceFor10AMOnJune15() throws Exception {
        String applicationDate = "2020-06-15T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void test5_shouldReturnPriceFor9PMOnJune16() throws Exception {
        String applicationDate = "2020-06-16T21:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void shouldReturnNotFoundWhenNoPriceIsAvailable() throws Exception {
        UUID invalidProductId = UUID.randomUUID();
        UUID invalidBrandId = UUID.randomUUID();
        String applicationDate = "2020-06-14T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", invalidProductId.toString())
                        .param("brandId", invalidBrandId.toString())
                        .param("applicationDate", applicationDate))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Price not found"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidUUIDIsProvided() throws Exception {
        String invalidProductId = "invalid-uuid";
        String validDate = "2020-06-14T10:00:00";

        mockMvc.perform(get("/api/prices")
                        .param("productId", invalidProductId)
                        .param("brandId", brandId.toString())
                        .param("applicationDate", validDate))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid UUID format"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidDateIsProvided() throws Exception {
        String invalidDate = "invalid-date";

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", invalidDate))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid date format"));
    }
}