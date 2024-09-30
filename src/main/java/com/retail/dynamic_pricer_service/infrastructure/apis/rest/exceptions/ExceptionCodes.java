package com.retail.dynamic_pricer_service.infrastructure.apis.rest.exceptions;

public enum ExceptionCodes {
    BAD_REQUEST("400"),
    NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500");

    public final String code;

    ExceptionCodes(String code) {
        this.code = code;
    }
}
