package com.invex.employee_service.infrastructure.adapter.in.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequest {

    @Size(
            max = 50,
            message = "First name must not exceed 50 characters"
    )
    private String firstName;

    @Size(
            max = 50,
            message = "Middle name must not exceed 50 characters"
    )
    private String middleName;

    @Size(
            max = 50,
            message = "Paternal last name must not exceed 50 characters"
    )
    private String paternalLastName;

    @Size(
            max = 50,
            message = "Maternal last name must not exceed 50 characters"
    )
    private String maternalLastName;

    @Min(
            value = 18,
            message = "Employee must be at least 18 years old"
    )
    private Integer age;

    @Pattern(
            regexp = "^(M|F)$",
            message = "Gender must be 'M' or 'F'"
    )
    private String gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @Size(
            max = 100,
            message = "Position must not exceed 100 characters"
    )
    private String position;
}
