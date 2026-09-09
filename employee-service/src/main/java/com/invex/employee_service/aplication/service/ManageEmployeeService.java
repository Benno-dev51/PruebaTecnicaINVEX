package com.invex.employee_service.aplication.service;

import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.ManageEmployeeUseCase;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;

import java.time.Clock;
import java.util.List;

public class ManageEmployeeService implements ManageEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;
    private final Clock clock; // CORRECCIÓN: Inyectamos el reloj para la fecha

    public ManageEmployeeService(EmployeeRepositoryPort employeeRepositoryPort, Clock clock) {
        this.employeeRepositoryPort = employeeRepositoryPort;
        this.clock = clock;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        employee.initializeRegistration(clock);
        return employeeRepositoryPort.save(employee);
    }

    @Override
    public List<Employee> createEmployeesBulk(List<Employee> employees) {
        // CORRECCIÓN: Usamos forEach para mutar explícitamente en lugar de peek()
        employees.forEach(emp -> emp.initializeRegistration(clock));
        return employeeRepositoryPort.saveAll(employees);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeData, boolean isPartialUpdate) {
        Employee existingEmployee = employeeRepositoryPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        // CORRECCIÓN: Usamos el método de dominio con reglas claras, delegando la responsabilidad
        existingEmployee.updateProfile(
                employeeData.getFirstName(),
                employeeData.getMiddleName(),
                employeeData.getPaternalLastName(),
                employeeData.getMaternalLastName(),
                employeeData.getAge(),
                employeeData.getGender(),
                employeeData.getDateOfBirth(),
                employeeData.getPosition()
        );

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