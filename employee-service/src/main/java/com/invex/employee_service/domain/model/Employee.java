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

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder paternalLastName(String paternalLastName) {
            this.paternalLastName = paternalLastName;
            return this;
        }

        public Builder maternalLastName(String maternalLastName) {
            this.maternalLastName = maternalLastName;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder position(String position) {
            this.position = position;
            return this;
        }

        public Builder registrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}