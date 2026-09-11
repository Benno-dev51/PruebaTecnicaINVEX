package com.invex.employee_service.domain.model.port.in;

import java.time.LocalDate;

public record CreateEmployeeCommand(
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