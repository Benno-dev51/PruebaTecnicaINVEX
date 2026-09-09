package com.invex.employee_service.domain.model.port.out;

import com.invex.employee_service.domain.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {
    Employee save(Employee employee);
    List<Employee> saveAll(List<Employee> employees);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    List<Employee> findByPartialName(String name);
    void deleteById(Long id);
}