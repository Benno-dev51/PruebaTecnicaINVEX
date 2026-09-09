package com.invex.employee_service.domain.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Employee(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.paternalLastName = builder.paternalLastName;
        this.maternalLastName = builder.maternalLastName;
        this.age = builder.age;
        this.gender = builder.gender;
        this.dateOfBirth = builder.dateOfBirth;
        this.position = builder.position;
        this.registrationDate = builder.registrationDate;
        this.active = builder.active;
    }

    public void markAsInactive() {
        this.active = false;
    }

    public void markAsActive() {
        this.active = true;
    }

    public void initializeRegistration(Clock clock) {
        this.registrationDate = LocalDateTime.now(clock);
        this.active = true;
    }

    public void updateProfile(
            String firstName,
            String middleName,
            String paternalLastName,
            String maternalLastName,
            Integer age,
            String gender,
            LocalDate dateOfBirth,
            String position) {

        if (firstName != null) {
            this.firstName = firstName;
        }

        if (middleName != null) {
            this.middleName = middleName;
        }

        if (paternalLastName != null) {
            this.paternalLastName = paternalLastName;
        }

        if (maternalLastName != null) {
            this.maternalLastName = maternalLastName;
        }

        if (age != null) {
            this.age = age;
        }

        if (gender != null) {
            this.gender = gender;
        }

        if (dateOfBirth != null) {
            this.dateOfBirth = dateOfBirth;
        }

        if (position != null) {
            this.position = position;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

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

        // ... builders ...

        public Employee build() {
            return new Employee(this);
        }
    }
}