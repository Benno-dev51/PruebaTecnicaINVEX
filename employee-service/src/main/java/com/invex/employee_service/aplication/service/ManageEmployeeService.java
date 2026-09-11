package com.invex.employee_service.aplication.service;

import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.CreateEmployeeCommand;
import com.invex.employee_service.domain.model.port.in.ManageEmployeeUseCase;
import com.invex.employee_service.domain.model.port.in.UpdateEmployeeCommand;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ManageEmployeeService implements ManageEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;
    private final Clock clock;

    @Override
    @Transactional
    public Employee createEmployee(
            CreateEmployeeCommand command) {

        log.info("Creating employee");

        Employee employee = Employee.create(
                command.firstName(),
                command.middleName(),
                command.paternalLastName(),
                command.maternalLastName(),
                command.age(),
                command.gender(),
                command.dateOfBirth(),
                command.position(),
                clock
        );

        Employee savedEmployee =
                employeeRepositoryPort.save(employee);

        log.info(
                "Employee created successfully with id: {}",
                savedEmployee.getId()
        );

        return savedEmployee;
    }

    @Override
    @Transactional
    public List<Employee> createEmployeesBulk(
            List<CreateEmployeeCommand> commands) {

        log.info(
                "Creating employees in bulk. Total employees: {}",
                commands.size()
        );

        List<Employee> employees = commands.stream()
                .map(command -> Employee.create(
                        command.firstName(),
                        command.middleName(),
                        command.paternalLastName(),
                        command.maternalLastName(),
                        command.age(),
                        command.gender(),
                        command.dateOfBirth(),
                        command.position(),
                        clock
                ))
                .toList();

        List<Employee> savedEmployees =
                employeeRepositoryPort.saveAll(employees);

        log.info(
                "Employees created successfully in bulk. Total created: {}",
                savedEmployees.size()
        );

        return savedEmployees;
    }

    @Override
    @Transactional
    public Employee updateEmployee(
            Long id,
            UpdateEmployeeCommand command) {

        log.info(
                "Updating employee with id: {}",
                id
        );

        Employee employee = employeeRepositoryPort.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        employee.updateProfile(
                command.firstName(),
                command.middleName(),
                command.paternalLastName(),
                command.maternalLastName(),
                command.age(),
                command.gender(),
                command.dateOfBirth(),
                command.position(),
                clock
        );

        Employee updatedEmployee =
                employeeRepositoryPort.save(employee);

        log.info(
                "Employee updated successfully with id: {}",
                updatedEmployee.getId()
        );

        return updatedEmployee;
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {

        log.info(
                "Deleting employee with id: {}",
                id
        );

        Employee employee = employeeRepositoryPort.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );

        employeeRepositoryPort.deleteById(
                employee.getId()
        );

        log.info(
                "Employee deleted successfully with id: {}",
                employee.getId()
        );
    }
}