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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveEmployeeServiceTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    @InjectMocks
    private RetrieveEmployeeService retrieveEmployeeService;

    @Test
    void shouldReturnEmployeeWhenIdExists() {
        Employee expectedEmployee = Employee.builder().build();
        ReflectionTestUtils.setField(expectedEmployee, "id", 1L);
        ReflectionTestUtils.setField(expectedEmployee, "firstName", "Benito");

        when(employeeRepositoryPort.findById(1L)).thenReturn(Optional.of(expectedEmployee));

        Employee result = retrieveEmployeeService.getEmployeeById(1L);

        assertEquals("Benito", result.getFirstName());
        verify(employeeRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        when(employeeRepositoryPort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> retrieveEmployeeService.getEmployeeById(99L));
    }

    @Test
    void shouldReturnEmptyListWhenSearchNameIsBlankOrNull() {
        List<Employee> resultWithNull = retrieveEmployeeService.searchEmployeesByName(null);
        List<Employee> resultWithEmpty = retrieveEmployeeService.searchEmployeesByName("   ");

        assertTrue(resultWithNull.isEmpty());
        assertTrue(resultWithEmpty.isEmpty());
        verify(employeeRepositoryPort, never()).findAll();
        verify(employeeRepositoryPort, never()).findByPartialName(anyString());
    }

    @Test
    void shouldReturnMatchingEmployeesWhenSearchNameIsProvided() {
        Employee expectedEmployee = Employee.builder().build();
        ReflectionTestUtils.setField(expectedEmployee, "firstName", "Benito");

        List<Employee> matchingEmployees = List.of(expectedEmployee);
        when(employeeRepositoryPort.findByPartialName("Ben")).thenReturn(matchingEmployees);

        List<Employee> result = retrieveEmployeeService.searchEmployeesByName("  Ben  ");

        assertEquals(1, result.size());
        verify(employeeRepositoryPort, times(1)).findByPartialName("Ben");
    }
}