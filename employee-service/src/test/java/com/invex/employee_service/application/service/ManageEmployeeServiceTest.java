package com.invex.employee_service.application.service;

import com.invex.employee_service.aplication.service.ManageEmployeeService;
import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.CreateEmployeeCommand;
import com.invex.employee_service.domain.model.port.in.UpdateEmployeeCommand;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageEmployeeServiceTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private ManageEmployeeService manageEmployeeService;

    private final Clock fixedClock = Clock.fixed(
            Instant.parse("2026-09-09T10:00:00Z"),
            ZoneId.of("UTC")
    );

    @BeforeEach
    void setUp() {
        manageEmployeeService = new ManageEmployeeService(
                employeeRepositoryPort,
                fixedClock
        );
    }

    @Test
    void shouldCreateAndSaveEmployee() {

        CreateEmployeeCommand command = new CreateEmployeeCommand(
                "Celia",
                null,
                "Perez",
                "Lopez",
                31,
                "F",
                LocalDate.of(1995, 8, 15),
                "Backend Developer"
        );

        when(employeeRepositoryPort.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Employee result =
                manageEmployeeService.createEmployee(command);

        assertNotNull(result);
        assertEquals("Celia", result.getFirstName());
        assertEquals("Perez", result.getPaternalLastName());
        assertEquals("Lopez", result.getMaternalLastName());
        assertEquals(31, result.getAge());
        assertEquals("F", result.getGender());
        assertEquals(
                LocalDate.of(1995, 8, 15),
                result.getDateOfBirth()
        );
        assertEquals(
                "Backend Developer",
                result.getPosition()
        );

        assertNotNull(result.getRegistrationDate());
        assertTrue(result.isActive());

        verify(employeeRepositoryPort, times(1))
                .save(any(Employee.class));
    }

    @Test
    void shouldCreateAndSaveBulkEmployees() {

        CreateEmployeeCommand firstCommand =
                new CreateEmployeeCommand(
                        "Benito",
                        null,
                        "Cruz",
                        "Lopez",
                        31,
                        "M",
                        LocalDate.of(1995, 8, 15),
                        "Backend Developer"
                );

        CreateEmployeeCommand secondCommand =
                new CreateEmployeeCommand(
                        "Celia",
                        null,
                        "Perez",
                        "Garcia",
                        28,
                        "F",
                        LocalDate.of(1998, 3, 20),
                        "QA Engineer"
                );

        List<CreateEmployeeCommand> commands =
                List.of(
                        firstCommand,
                        secondCommand
                );

        when(employeeRepositoryPort.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Employee> result =
                manageEmployeeService.createEmployeesBulk(commands);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(
                "Benito",
                result.get(0).getFirstName()
        );

        assertEquals(
                "Celia",
                result.get(1).getFirstName()
        );

        assertNotNull(
                result.get(0).getRegistrationDate()
        );

        assertNotNull(
                result.get(1).getRegistrationDate()
        );

        assertTrue(result.get(0).isActive());
        assertTrue(result.get(1).isActive());

        verify(employeeRepositoryPort, times(1))
                .saveAll(anyList());
    }

    @Test
    void shouldUpdateEmployeeWhenExists() {

        Employee existingEmployee = createExistingEmployee();

        when(employeeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepositoryPort.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UpdateEmployeeCommand command =
                new UpdateEmployeeCommand(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "Tech Lead"
                );

        Employee result =
                manageEmployeeService.updateEmployee(
                        1L,
                        command
                );

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(
                "Tech Lead",
                result.getPosition()
        );

        assertEquals(
                "Juan",
                result.getFirstName()
        );

        verify(employeeRepositoryPort, times(1))
                .findById(1L);

        verify(employeeRepositoryPort, times(1))
                .save(existingEmployee);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentEmployee() {

        when(employeeRepositoryPort.findById(99L))
                .thenReturn(Optional.empty());

        UpdateEmployeeCommand command =
                new UpdateEmployeeCommand(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "Tech Lead"
                );

        EmployeeNotFoundException exception =
                assertThrows(
                        EmployeeNotFoundException.class,
                        () -> manageEmployeeService.updateEmployee(
                                99L,
                                command
                        )
                );

        assertEquals(
                "Employee not found with id: 99",
                exception.getMessage()
        );

        verify(employeeRepositoryPort, times(1))
                .findById(99L);

        verify(employeeRepositoryPort, never())
                .save(any(Employee.class));
    }

    @Test
    void shouldDeleteEmployeeWhenExists() {

        Employee existingEmployee = createExistingEmployee();

        when(employeeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        manageEmployeeService.deleteEmployee(1L);

        verify(employeeRepositoryPort, times(1))
                .findById(1L);

        verify(employeeRepositoryPort, times(1))
                .deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEmployee() {

        when(employeeRepositoryPort.findById(99L))
                .thenReturn(Optional.empty());

        EmployeeNotFoundException exception =
                assertThrows(
                        EmployeeNotFoundException.class,
                        () -> manageEmployeeService.deleteEmployee(99L)
                );

        assertEquals(
                "Employee not found with id: 99",
                exception.getMessage()
        );

        verify(employeeRepositoryPort, times(1))
                .findById(99L);

        verify(employeeRepositoryPort, never())
                .deleteById(anyLong());
    }

    private Employee createExistingEmployee() {

        return Employee.reconstitute(
                1L,
                "Juan",
                null,
                "Perez",
                "Lopez",
                31,
                "M",
                LocalDate.of(1995, 8, 15),
                "Backend Developer",
                LocalDateTime.of(
                        2026,
                        1,
                        10,
                        10,
                        0
                ),
                true
        );
    }
}