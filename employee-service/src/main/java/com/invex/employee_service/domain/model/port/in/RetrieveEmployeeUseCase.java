package com.invex.employee_service.domain.model.port.in;

import com.invex.employee_service.domain.model.Employee;
import java.util.List;

public interface RetrieveEmployeeUseCase {
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    List<Employee> searchEmployeesByName(String name);
}