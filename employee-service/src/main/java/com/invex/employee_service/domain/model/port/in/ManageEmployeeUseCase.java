package com.invex.employee_service.domain.model.port.in;

import com.invex.employee_service.domain.model.Employee;

import java.util.List;

public interface ManageEmployeeUseCase {
    Employee createEmployee(Employee employee);
    List<Employee> createEmployeesBulk(List<Employee> employees);
    Employee updateEmployee(Long id, Employee employeeData, boolean isPartialUpdate);
    void deleteEmployee(Long id);
}
