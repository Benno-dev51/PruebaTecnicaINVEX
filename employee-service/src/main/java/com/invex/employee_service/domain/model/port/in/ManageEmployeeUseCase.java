package com.invex.employee_service.domain.model.port.in;

import com.invex.employee_service.domain.model.Employee;

import java.util.List;

public interface ManageEmployeeUseCase {

    Employee createEmployee(
            CreateEmployeeCommand command
    );

    List<Employee> createEmployeesBulk(
            List<CreateEmployeeCommand> commands
    );

    Employee updateEmployee(
            Long id,
            UpdateEmployeeCommand command
    );

    void deleteEmployee(Long id);
}