package com.invex.employee_service.domain.exception;

public class BussinessValidationException extends RuntimeException {
    public BussinessValidationException(String message) {
        super(message);
    }
}