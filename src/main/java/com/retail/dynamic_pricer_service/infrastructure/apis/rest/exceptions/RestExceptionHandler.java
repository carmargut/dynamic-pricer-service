package com.retail.dynamic_pricer_service.infrastructure.apis.rest.exceptions;

import com.retail.dynamic_pricer_service.domain.exceptions.DomainException;
import com.retail.dynamic_pricer_service.domain.exceptions.EntityNotFoundException;
import com.retail.dynamic_pricer_service.domain.exceptions.ValidationException;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestErrorResponse> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ex.getMessage(), String.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ex.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<RestErrorResponse> handleDomainException(DomainException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ex.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new RestErrorResponse("Invalid UUID format", String.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    }

}