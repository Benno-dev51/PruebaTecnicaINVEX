package com.invex.employee_service.domain.model.port.in;


import java.time.LocalDate;

public record UpdateEmployeeCommand(
        String firstName,
        String middleName,
        String paternalLastName,
        String maternalLastName,
        Integer age,
        String gender,
        LocalDate dateOfBirth,
        String position
) {
}