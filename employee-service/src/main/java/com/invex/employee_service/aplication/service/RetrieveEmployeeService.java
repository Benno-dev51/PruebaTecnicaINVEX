package com.invex.employee_service.aplication.service;

import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.RetrieveEmployeeUseCase;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import java.util.List;

public class RetrieveEmployeeService implements RetrieveEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public RetrieveEmployeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepositoryPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepositoryPort.findAll();
    }

    @Override
    public List<Employee> searchEmployeesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllEmployees();
        }
        return employeeRepositoryPort.findByPartialName(name.trim());
    }
}