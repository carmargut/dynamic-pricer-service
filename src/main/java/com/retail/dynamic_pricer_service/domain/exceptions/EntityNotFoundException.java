package com.retail.dynamic_pricer_service.domain.exceptions;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message, "404");
    }
}