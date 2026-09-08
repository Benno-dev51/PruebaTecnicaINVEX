package com.invex.employee_service.aplication.service;

import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.ManageEmployeeUseCase;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;

import java.util.List;
import java.util.stream.Collectors;

public class ManageEmployeeService implements ManageEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public ManageEmployeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        employee.initializeRegistration();
        return employeeRepositoryPort.save(employee);
    }

    @Override
    public List<Employee> createEmployeesBulk(List<Employee> employees) {
        List<Employee> preparedEmployees = employees.stream()
                .peek(Employee::initializeRegistration)
                .collect(Collectors.toList());
        return employeeRepositoryPort.saveAll(preparedEmployees);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeData, boolean isPartialUpdate) {
        Employee existingEmployee = employeeRepositoryPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        if (isPartialUpdate) {
            existingEmployee.updateNonNullFields(employeeData);
        } else {
            existingEmployee.updateAllFields(employeeData);
        }

        return employeeRepositoryPort.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (employeeRepositoryPort.findById(id).isEmpty()) {
            throw new EmployeeNotFoundException("Cannot delete. Employee not found with id: " + id);
        }
        employeeRepositoryPort.deleteById(id);
    }
}