package com.invex.employee_service.domain.model;

import com.invex.employee_service.domain.exception.BussinessValidationException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Employee {

    private Long id;
    private String firstName;
    private String middleName;
    private String paternalLastName;
    private String maternalLastName;
    private Integer age;
    private String gender;
    private LocalDate dateOfBirth;
    private String position;
    private LocalDateTime registrationDate;
    private boolean active;

    private Employee(
            String firstName,
            String middleName,
            String paternalLastName,
            String maternalLastName,
            Integer age,
            String gender,
            LocalDate dateOfBirth,
            String position) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.paternalLastName = paternalLastName;
        this.maternalLastName = maternalLastName;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
    }

    public static Employee create(
            String firstName,
            String middleName,
            String paternalLastName,
            String maternalLastName,
            Integer age,
            String gender,
            LocalDate dateOfBirth,
            String position,
            Clock clock) {

        validateProfile(
                firstName,
                paternalLastName,
                maternalLastName,
                age,
                gender,
                dateOfBirth,
                position,
                clock
        );

        Employee employee = new Employee(
                firstName,
                middleName,
                paternalLastName,
                maternalLastName,
                age,
                gender,
                dateOfBirth,
                position
        );

        employee.initializeRegistration(clock);

        return employee;
    }

    public static Employee reconstitute(
            Long id,
            String firstName,
            String middleName,
            String paternalLastName,
            String maternalLastName,
            Integer age,
            String gender,
            LocalDate dateOfBirth,
            String position,
            LocalDateTime registrationDate,
            boolean active) {

        Employee employee = new Employee(
                firstName,
                middleName,
                paternalLastName,
                maternalLastName,
                age,
                gender,
                dateOfBirth,
                position
        );

        employee.id = id;
        employee.registrationDate = registrationDate;
        employee.active = active;

        return employee;
    }

    public void updateProfile(
            String firstName,
            String middleName,
            String paternalLastName,
            String maternalLastName,
            Integer age,
            String gender,
            LocalDate dateOfBirth,
            String position,
            Clock clock) {

        String newFirstName =
                firstName != null ? firstName : this.firstName;

        String newMiddleName =
                middleName != null ? middleName : this.middleName;

        String newPaternalLastName =
                paternalLastName != null
                        ? paternalLastName
                        : this.paternalLastName;

        String newMaternalLastName =
                maternalLastName != null
                        ? maternalLastName
                        : this.maternalLastName;

        Integer newAge =
                age != null ? age : this.age;

        String newGender =
                gender != null ? gender : this.gender;

        LocalDate newDateOfBirth =
                dateOfBirth != null
                        ? dateOfBirth
                        : this.dateOfBirth;

        String newPosition =
                position != null ? position : this.position;

        validateProfile(
                newFirstName,
                newPaternalLastName,
                newMaternalLastName,
                newAge,
                newGender,
                newDateOfBirth,
                newPosition,
                clock
        );

        this.firstName = newFirstName;
        this.middleName = newMiddleName;
        this.paternalLastName = newPaternalLastName;
        this.maternalLastName = newMaternalLastName;
        this.age = newAge;
        this.gender = newGender;
        this.dateOfBirth = newDateOfBirth;
        this.position = newPosition;
    }

    public void markAsInactive() {
        this.active = false;
    }

    public void markAsActive() {
        this.active = true;
    }

    private void initializeRegistration(Clock clock) {
        this.registrationDate = LocalDateTime.now(clock);
        this.active = true;
    }

    private static void validateProfile(
            String firstName,
            String paternalLastName,
            String maternalLastName,
            Integer age,
            String gender,
            LocalDate dateOfBirth,
            String position,
            Clock clock) {

        if (firstName == null || firstName.isBlank()) {
            throw new BussinessValidationException(
                    "First name is mandatory"
            );
        }

        if (paternalLastName == null || paternalLastName.isBlank()) {
            throw new BussinessValidationException(
                    "Paternal last name is mandatory"
            );
        }

        if (maternalLastName == null || maternalLastName.isBlank()) {
            throw new BussinessValidationException(
                    "Maternal last name is mandatory"
            );
        }

        if (age == null || age < 18) {
            throw new BussinessValidationException(
                    "Employee must be at least 18 years old"
            );
        }

        if (gender == null ||
                (!gender.equals("M") && !gender.equals("F"))) {

            throw new BussinessValidationException(
                    "Gender must be 'M' or 'F'"
            );
        }

        if (dateOfBirth == null ||
                !dateOfBirth.isBefore(LocalDate.now(clock))) {

            throw new BussinessValidationException(
                    "Date of birth must be in the past"
            );
        }

        int calculatedAge =
                Period.between(
                        dateOfBirth,
                        LocalDate.now(clock)
                ).getYears();

        if (age != calculatedAge) {
            throw new BussinessValidationException(
                    "Age does not match date of birth"
            );
        }

        if (position == null || position.isBlank()) {
            throw new BussinessValidationException(
                    "Position is mandatory"
            );
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPaternalLastName() {
        return paternalLastName;
    }

    public String getMaternalLastName() {
        return maternalLastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public boolean isActive() {
        return active;
    }
}