package com.invex.employee_service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldInitializeRegistrationCorrectly() {
        Employee employee = Employee.builder()
                .firstName("Carlos")
                .build();

        employee.initializeRegistration();

        assertNotNull(employee.getRegistrationDate(), "Registration date should be set");
        assertTrue(employee.getIsActive(), "Employee should be active upon registration");
    }

    @Test
    void shouldMarkAsInactive() {
        Employee employee = Employee.builder()
                .firstName("Carlos")
                .isActive(true)
                .build();

        employee.markAsInactive();

        assertFalse(employee.getIsActive(), "Employee should be marked as inactive");
    }

    @Test
    void shouldUpdateOnlyNonNullFieldsForPartialUpdate() {
        Employee existingEmployee = Employee.builder()
                .firstName("Celia")
                .paternalLastName("Cruz")
                .age(30)
                .build();

        // testing partial payload by setting only the age
        Employee updateData = Employee.builder()
                .age(35)
                .build();

        existingEmployee.updateNonNullFields(updateData);

        // CORRECCIÓN: Ahora validamos que sigan siendo Celia Cruz, ya que esos campos vinieron nulos en el update
        assertEquals("Celia", existingEmployee.getFirstName(), "First name should not change if null is provided");
        assertEquals("Cruz", existingEmployee.getPaternalLastName(), "Last name should not change if null is provided");
        assertEquals(35, existingEmployee.getAge(), "Age should be updated to 35");
    }

    @Test
    void shouldOverwriteAllFieldsForFullUpdate() {
        Employee existingEmployee = Employee.builder()
                .firstName("Pepe")
                .paternalLastName("Pecas")
                .age(30)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        // CORRECCIÓN: Los datos de actualización deben ser Benito Lopez para que el assert pase
        Employee updateData = Employee.builder()
                .firstName("Benito")
                .paternalLastName("Lopez")
                .age(28)
                .dateOfBirth(LocalDate.of(1992, 5, 5))
                .build();

        existingEmployee.updateAllFields(updateData);

        // CORRECCIÓN: Ahora el assert coincide con los datos que mandamos a actualizar
        assertEquals("Benito", existingEmployee.getFirstName());
        assertEquals("Lopez", existingEmployee.getPaternalLastName());
        assertEquals(28, existingEmployee.getAge());
        assertEquals(LocalDate.of(1992, 5, 5), existingEmployee.getDateOfBirth());
    }

    @Test
    void shouldMarkAsActive() {
        Employee employee = Employee.builder()
                .firstName("Carlos")
                .isActive(false)
                .build();

        employee.markAsActive();

        assertTrue(employee.getIsActive(), "Employee should be marked as active");
    }
}