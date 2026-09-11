package com.invex.employee_service.domain.model;

import com.invex.employee_service.domain.exception.BussinessValidationException;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private final Clock fixedClock = Clock.fixed(
            Instant.parse("2026-09-09T10:00:00Z"),
            ZoneId.of("UTC")
    );

    @Test
    void shouldCreateEmployeeCorrectly() {

        Employee employee = Employee.create(
                "Carlos",
                null,
                "Perez",
                "Lopez",
                31,
                "M",
                LocalDate.of(1995, 8, 15),
                "Backend Developer",
                fixedClock
        );

        assertEquals("Carlos", employee.getFirstName());
        assertEquals("Perez", employee.getPaternalLastName());
        assertEquals("Lopez", employee.getMaternalLastName());
        assertEquals(31, employee.getAge());
        assertEquals("M", employee.getGender());
        assertEquals(
                LocalDate.of(1995, 8, 15),
                employee.getDateOfBirth()
        );
        assertEquals(
                "Backend Developer",
                employee.getPosition()
        );

        assertNotNull(employee.getRegistrationDate());
        assertTrue(employee.isActive());
    }

    @Test
    void shouldInitializeRegistrationDateUsingProvidedClock() {

        Employee employee = Employee.create(
                "Carlos",
                null,
                "Perez",
                "Lopez",
                31,
                "M",
                LocalDate.of(1995, 8, 15),
                "Backend Developer",
                fixedClock
        );

        LocalDateTime expectedRegistrationDate =
                LocalDateTime.of(
                        2026,
                        9,
                        9,
                        10,
                        0
                );

        assertEquals(
                expectedRegistrationDate,
                employee.getRegistrationDate()
        );
    }

    @Test
    void shouldCreateEmployeeAsActive() {

        Employee employee = createValidEmployee();

        assertTrue(employee.isActive());
    }

    @Test
    void shouldMarkEmployeeAsInactive() {

        Employee employee = createValidEmployee();

        employee.markAsInactive();

        assertFalse(employee.isActive());
    }

    @Test
    void shouldMarkEmployeeAsActive() {

        Employee employee = createValidEmployee();

        employee.markAsInactive();

        assertFalse(employee.isActive());

        employee.markAsActive();

        assertTrue(employee.isActive());
    }

    @Test
    void shouldUpdateOnlyProvidedFields() {

        Employee employee = createValidEmployee();

        employee.updateProfile(
                "Benito",
                null,
                null,
                null,
                null,
                null,
                null,
                "Tech Lead",
                fixedClock
        );

        assertEquals("Benito", employee.getFirstName());
        assertEquals("Perez", employee.getPaternalLastName());
        assertEquals("Lopez", employee.getMaternalLastName());
        assertEquals(31, employee.getAge());
        assertEquals("M", employee.getGender());
        assertEquals(
                LocalDate.of(1995, 8, 15),
                employee.getDateOfBirth()
        );
        assertEquals("Tech Lead", employee.getPosition());
    }

    @Test
    void shouldThrowExceptionWhenFirstNameIsBlank() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        " ",
                        null,
                        "Perez",
                        "Lopez",
                        31,
                        "M",
                        LocalDate.of(1995, 8, 15),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenPaternalLastNameIsBlank() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        " ",
                        "Lopez",
                        31,
                        "M",
                        LocalDate.of(1995, 8, 15),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenMaternalLastNameIsBlank() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        "Perez",
                        " ",
                        31,
                        "M",
                        LocalDate.of(1995, 8, 15),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenEmployeeIsUnderAge() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        "Perez",
                        "Lopez",
                        17,
                        "M",
                        LocalDate.of(2009, 8, 15),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenGenderIsInvalid() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        "Perez",
                        "Lopez",
                        31,
                        "X",
                        LocalDate.of(1995, 8, 15),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenDateOfBirthIsInFuture() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        "Perez",
                        "Lopez",
                        31,
                        "M",
                        LocalDate.of(2030, 1, 1),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenAgeDoesNotMatchDateOfBirth() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        "Perez",
                        "Lopez",
                        40,
                        "M",
                        LocalDate.of(1995, 8, 15),
                        "Backend Developer",
                        fixedClock
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenPositionIsBlank() {

        assertThrows(
                BussinessValidationException.class,
                () -> Employee.create(
                        "Carlos",
                        null,
                        "Perez",
                        "Lopez",
                        31,
                        "M",
                        LocalDate.of(1995, 8, 15),
                        " ",
                        fixedClock
                )
        );
    }

    @Test
    void shouldReconstituteExistingEmployee() {

        LocalDateTime registrationDate =
                LocalDateTime.of(
                        2026,
                        1,
                        10,
                        10,
                        0
                );

        Employee employee = Employee.reconstitute(
                10L,
                "Carlos",
                null,
                "Perez",
                "Lopez",
                31,
                "M",
                LocalDate.of(1995, 8, 15),
                "Backend Developer",
                registrationDate,
                false
        );

        assertEquals(10L, employee.getId());
        assertEquals("Carlos", employee.getFirstName());
        assertEquals(registrationDate, employee.getRegistrationDate());
        assertFalse(employee.isActive());
    }

    private Employee createValidEmployee() {

        return Employee.create(
                "Carlos",
                null,
                "Perez",
                "Lopez",
                31,
                "M",
                LocalDate.of(1995, 8, 15),
                "Backend Developer",
                fixedClock
        );
    }
}