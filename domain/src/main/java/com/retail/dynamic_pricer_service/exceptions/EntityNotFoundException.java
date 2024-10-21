package com.retail.dynamic_pricer_service.exceptions;

public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}