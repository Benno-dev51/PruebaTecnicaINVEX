package com.invex.employee_service.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private final Clock fixedClock = Clock.fixed(Instant.parse("2026-09-09T10:00:00Z"), ZoneId.of("UTC"));

    @Test
    void shouldInitializeRegistrationCorrectly() {
        // Usamos el builder vacío que sí tienes
        Employee employee = Employee.builder().build();
        ReflectionTestUtils.setField(employee, "firstName", "Carlos");

        employee.initializeRegistration(fixedClock);

        assertNotNull(employee.getRegistrationDate(), "Registration date should be set");
        assertTrue(employee.isActive(), "Employee should be active upon registration");
    }

    @Test
    void shouldMarkAsInactive() {
        Employee employee = Employee.builder().build();
        employee.markAsActive();
        employee.markAsInactive();

        assertFalse(employee.isActive(), "Employee should be marked as inactive");
    }

    @Test
    void shouldMarkAsActive() {
        Employee employee = Employee.builder().build();
        // Por defecto al construirse vacío, el boolean primitive es false
        employee.markAsActive();

        assertTrue(employee.isActive(), "Employee should be marked as active");
    }

    @Test
    void shouldUpdateProfileIgnoringNullValues() {
        Employee existingEmployee = Employee.builder().build();
        ReflectionTestUtils.setField(existingEmployee, "firstName", "Celia");
        ReflectionTestUtils.setField(existingEmployee, "paternalLastName", "Cruz");
        ReflectionTestUtils.setField(existingEmployee, "age", 30);

        // Actualizamos solo la edad y el nombre, mandando null en lo demás
        existingEmployee.updateProfile("Benito", null, null, null, 35, null, null, null);

        assertEquals("Benito", existingEmployee.getFirstName());
        assertEquals("Cruz", existingEmployee.getPaternalLastName(), "Paternal last name should not change");
        assertEquals(35, existingEmployee.getAge(), "Age should be updated to 35");
    }
}