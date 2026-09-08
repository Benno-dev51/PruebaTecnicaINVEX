package com.invex.employee_service.domain.model;

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
    private Boolean isActive;

    // Private constructor to enforce object creation via Builder
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
        this.isActive = builder.isActive;
    }

    // --- Rich Domain Behaviors (Business Logic) ---

    public void markAsInactive() {
        this.isActive = false;
    }

    public void markAsActive() {
        this.isActive = true;
    }

    public void initializeRegistration() {
        this.registrationDate = LocalDateTime.now();
        this.isActive = true;
    }

    // --- Getters ---

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getPaternalLastName() { return paternalLastName; }
    public String getMaternalLastName() { return maternalLastName; }
    public Integer getAge() { return age; }
    public String getGender() { return gender; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getPosition() { return position; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public Boolean getIsActive() { return isActive; }

    // --- Setters for Updatable Fields ---
    // Note: ID and RegistrationDate do not have setters as they shouldn't be modified after creation.

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setPaternalLastName(String paternalLastName) { this.paternalLastName = paternalLastName; }
    public void setMaternalLastName(String maternalLastName) { this.maternalLastName = maternalLastName; }
    public void setAge(Integer age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setPosition(String position) { this.position = position; }
    public void setIsActive(Boolean active) { isActive = active; }

    // --- Native Builder Pattern ---

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
        private Boolean isActive;

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

        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}