package unit.model;

import com.retail.dynamic_pricer_service.exceptions.ValidationException;
import com.retail.dynamic_pricer_service.model.Price;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceTest {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID productId;
    private UUID brandId;
    private BigDecimal price;
    private String currency;

    @BeforeEach
    void setUp() {
        startDate = LocalDateTime.of(2024, 9, 1, 10, 0, 0);
        endDate = LocalDateTime.of(2024, 12, 1, 10, 0, 0);
        productId = UUID.randomUUID();
        brandId = UUID.randomUUID();
        price = new BigDecimal("35.50");
        currency = "EUR";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldCreateValidPrice() {
        Price priceObj = new Price(UUID.randomUUID(), brandId, productId, startDate, endDate, 1, price, currency);
        assertEquals(productId, priceObj.productId());
        assertEquals(brandId, priceObj.brandId());
        assertEquals(startDate, priceObj.startDate());
        assertEquals(endDate, priceObj.endDate());
        assertEquals(price, priceObj.price());
        assertEquals(currency, priceObj.currency());
        assertEquals(1, priceObj.priority());
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), brandId, productId, null, endDate, 1, price, currency)
        );
        assertEquals("Start date cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), brandId, productId, startDate, null, 1, price, currency)
        );
        assertEquals("End date cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), brandId, null, startDate, endDate, 1, price, currency)
        );
        assertEquals("Product ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandIdIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), null, productId, startDate, endDate, 1, price, currency)
        );
        assertEquals("Brand ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), brandId, productId, startDate, endDate, 1, null, currency)
        );
        assertEquals("Price cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), brandId, productId, startDate, endDate, 1, price, null)
        );
        assertEquals("Currency cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        LocalDateTime invalidEndDate = LocalDateTime.of(2023, 8, 1, 10, 0, 0);
        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Price(UUID.randomUUID(), brandId, productId, startDate, invalidEndDate, 1, price, currency)
        );
        assertEquals("End date cannot be before start date", exception.getMessage());
    }

}