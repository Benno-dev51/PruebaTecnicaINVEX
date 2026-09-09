package com.invex.employee_service.application.service;

import com.invex.employee_service.aplication.service.ManageEmployeeService;
import com.invex.employee_service.domain.exception.EmployeeNotFoundException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageEmployeeServiceTest {

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    private ManageEmployeeService manageEmployeeService;
    private final Clock fixedClock = Clock.fixed(Instant.parse("2026-09-09T10:00:00Z"), ZoneId.of("UTC"));

    @BeforeEach
    void setUp() {
        manageEmployeeService = new ManageEmployeeService(employeeRepositoryPort, fixedClock);
    }

    @Test
    void shouldInitializeAndSaveEmployee() {
        Employee newEmployee = Employee.builder().build();
        ReflectionTestUtils.setField(newEmployee, "firstName", "Celia");

        when(employeeRepositoryPort.save(any(Employee.class))).thenReturn(newEmployee);

        manageEmployeeService.createEmployee(newEmployee);

        assertNotNull(newEmployee.getRegistrationDate());
        assertTrue(newEmployee.isActive());
        verify(employeeRepositoryPort, times(1)).save(newEmployee);
    }

    @Test
    void shouldInitializeAndSaveBulkEmployees() {
        Employee emp1 = Employee.builder().build();
        ReflectionTestUtils.setField(emp1, "firstName", "Benito");

        Employee emp2 = Employee.builder().build();
        ReflectionTestUtils.setField(emp2, "firstName", "Celia");

        List<Employee> newEmployees = List.of(emp1, emp2);

        when(employeeRepositoryPort.saveAll(anyList())).thenReturn(newEmployees);

        List<Employee> result = manageEmployeeService.createEmployeesBulk(newEmployees);

        assertEquals(2, result.size());
        assertNotNull(newEmployees.get(0).getRegistrationDate());
        assertNotNull(newEmployees.get(1).getRegistrationDate());
        verify(employeeRepositoryPort, times(1)).saveAll(newEmployees);
    }

    @Test
    void shouldDeleteEmployeeWhenExists() {
        Employee existingEmployee = Employee.builder().build();
        ReflectionTestUtils.setField(existingEmployee, "id", 1L);

        when(employeeRepositoryPort.findById(1L)).thenReturn(Optional.of(existingEmployee));

        manageEmployeeService.deleteEmployee(1L);

        verify(employeeRepositoryPort, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEmployee() {
        when(employeeRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> manageEmployeeService.deleteEmployee(99L));
        verify(employeeRepositoryPort, never()).deleteById(anyLong());
    }
}