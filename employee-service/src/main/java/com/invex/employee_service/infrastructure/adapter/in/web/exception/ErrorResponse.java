package com.invex.employee_service.infrastructure.adapter.in.web.exception;


public record ErrorResponse(
        int status,
        String code,
        String message
) {
}