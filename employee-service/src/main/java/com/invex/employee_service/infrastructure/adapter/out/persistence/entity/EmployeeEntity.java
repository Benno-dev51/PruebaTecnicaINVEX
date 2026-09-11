package com.invex.employee_service.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(
            name = "paternal_last_name",
            nullable = false
    )
    private String paternalLastName;

    @Column(
            name = "maternal_last_name",
            nullable = false
    )
    private String maternalLastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String gender;

    @Column(
            name = "date_of_birth",
            nullable = false
    )
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String position;

    @Column(
            name = "registration_date",
            nullable = false,
            updatable = false
    )
    private LocalDateTime registrationDate;

    @Column(
            name = "is_active",
            nullable = false
    )
    private Boolean isActive;
}