package com.invex.employee_service.aplication.service;

import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.RetrieveEmployeeUseCase;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RetrieveEmployeeService
        implements RetrieveEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    @Override
    public Employee getEmployeeById(Long id) {

        log.debug(
                "Retrieving employee with id: {}",
                id
        );

        return employeeRepositoryPort.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
    }

    @Override
    public List<Employee> getAllEmployees() {

        log.debug("Retrieving all employees");

        return employeeRepositoryPort.findAll();
    }

    @Override
    public List<Employee> searchEmployeesByName(String name) {

        log.debug("Searching employees by partial name");

        if (name == null || name.isBlank()) {

            log.debug(
                    "Employee search skipped because the provided name is blank"
            );

            return List.of();
        }

        String normalizedName = name.trim();

        return employeeRepositoryPort
                .findByPartialName(normalizedName);
    }
}