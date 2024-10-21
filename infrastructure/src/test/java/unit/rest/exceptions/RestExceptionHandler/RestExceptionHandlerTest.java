package unit.rest.exceptions.RestExceptionHandler;

import com.retail.dynamic_pricer_service.apis.rest.RestErrorResponse;
import com.retail.dynamic_pricer_service.apis.rest.exceptions.RestExceptionHandler;
import com.retail.dynamic_pricer_service.exceptions.DomainException;
import com.retail.dynamic_pricer_service.exceptions.EntityNotFoundException;
import com.retail.dynamic_pricer_service.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestExceptionHandlerTest {

    private RestExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new RestExceptionHandler();
    }

    @Test
    void shouldReturnBadRequestWhenValidationExceptionIsThrown() {
        ValidationException ex = new ValidationException("Validation error");
        ResponseEntity<RestErrorResponse> response = exceptionHandler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error", response.getBody().message());
        assertEquals("400", response.getBody().status());
    }

    @Test
    void shouldReturnNotFoundWhenEntityNotFoundExceptionIsThrown() {
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");
        ResponseEntity<RestErrorResponse> response = exceptionHandler.handleEntityNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entity not found", response.getBody().message());
        assertEquals("404", response.getBody().status());
    }

    @Test
    void shouldReturnInternalServerErrorWhenDomainExceptionIsThrown() {
        DomainException ex = new DomainException("Domain error");
        ResponseEntity<RestErrorResponse> response = exceptionHandler.handleDomainException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Domain error", response.getBody().message());
        assertEquals("500", response.getBody().status());
    }

    @Test
    void shouldReturnBadRequestWhenMethodArgumentTypeMismatchExceptionIsThrown() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(null, null, "uuid", null, null);
        ResponseEntity<RestErrorResponse> response = exceptionHandler.handleTypeMismatchException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid UUID format", response.getBody().message());
        assertEquals("400", response.getBody().status());
    }

}