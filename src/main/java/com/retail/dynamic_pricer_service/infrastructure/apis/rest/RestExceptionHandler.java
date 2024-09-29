package com.retail.dynamic_pricer_service.infrastructure.apis.rest;

import com.retail.dynamic_pricer_service.domain.exceptions.DomainException;
import com.retail.dynamic_pricer_service.domain.exceptions.EntityNotFoundException;
import com.retail.dynamic_pricer_service.domain.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestErrorResponse> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ex.getMessage(), ex.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ex.getMessage(), ex.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<RestErrorResponse> handleDomainException(DomainException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ex.getMessage(), ex.getErrorCode()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}