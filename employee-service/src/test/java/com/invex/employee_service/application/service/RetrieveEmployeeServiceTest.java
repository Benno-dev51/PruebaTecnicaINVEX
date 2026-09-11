package com.invex.employee_service.application.service;

import com.invex.employee_service.aplication.service.RetrieveEmployeeService;
import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveEmployeeServiceTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    @InjectMocks
    private RetrieveEmployeeService retrieveEmployeeService;

    @Test
    void shouldReturnEmployeeWhenIdExists() {

        Employee expectedEmployee = createEmployee(
                1L,
                "Benito"
        );

        when(employeeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(expectedEmployee));

        Employee result =
                retrieveEmployeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Benito", result.getFirstName());

        verify(employeeRepositoryPort, times(1))
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {

        when(employeeRepositoryPort.findById(99L))
                .thenReturn(Optional.empty());

        EmployeeNotFoundException exception =
                assertThrows(
                        EmployeeNotFoundException.class,
                        () -> retrieveEmployeeService
                                .getEmployeeById(99L)
                );

        assertEquals(
                "Employee not found with id: 99",
                exception.getMessage()
        );

        verify(employeeRepositoryPort, times(1))
                .findById(99L);
    }

    @Test
    void shouldReturnAllEmployees() {

        Employee firstEmployee = createEmployee(
                1L,
                "Benito"
        );

        Employee secondEmployee = createEmployee(
                2L,
                "Celia"
        );

        List<Employee> employees =
                List.of(
                        firstEmployee,
                        secondEmployee
                );

        when(employeeRepositoryPort.findAll())
                .thenReturn(employees);

        List<Employee> result =
                retrieveEmployeeService.getAllEmployees();

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

        verify(employeeRepositoryPort, times(1))
                .findAll();
    }

    @Test
    void shouldReturnEmptyListWhenSearchNameIsBlankOrNull() {

        List<Employee> resultWithNull =
                retrieveEmployeeService
                        .searchEmployeesByName(null);

        List<Employee> resultWithBlank =
                retrieveEmployeeService
                        .searchEmployeesByName("   ");

        assertTrue(resultWithNull.isEmpty());
        assertTrue(resultWithBlank.isEmpty());

        verify(employeeRepositoryPort, never())
                .findByPartialName(anyString());
    }

    @Test
    void shouldReturnMatchingEmployeesWhenSearchNameIsProvided() {

        Employee expectedEmployee = createEmployee(
                1L,
                "Benito"
        );

        List<Employee> matchingEmployees =
                List.of(expectedEmployee);

        when(employeeRepositoryPort.findByPartialName("Ben"))
                .thenReturn(matchingEmployees);

        List<Employee> result =
                retrieveEmployeeService
                        .searchEmployeesByName("  Ben  ");

        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(
                "Benito",
                result.get(0).getFirstName()
        );

        verify(employeeRepositoryPort, times(1))
                .findByPartialName("Ben");
    }

    private Employee createEmployee(
            Long id,
            String firstName) {

        return Employee.reconstitute(
                id,
                firstName,
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