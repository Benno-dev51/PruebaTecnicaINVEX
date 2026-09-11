package com.invex.employee_service.infrastructure.adapter.out.persistence.mapper;

import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeePersistenceMapper {

    public Employee toDomain(EmployeeEntity entity) {

        if (entity == null) {
            return null;
        }

        return Employee.reconstitute(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getPaternalLastName(),
                entity.getMaternalLastName(),
                entity.getAge(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPosition(),
                entity.getRegistrationDate(),
                entity.getIsActive() != null
                        && entity.getIsActive()
        );
    }

    public EmployeeEntity toEntity(Employee domain) {

        if (domain == null) {
            return null;
        }

        return EmployeeEntity.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .middleName(domain.getMiddleName())
                .paternalLastName(
                        domain.getPaternalLastName()
                )
                .maternalLastName(
                        domain.getMaternalLastName()
                )
                .age(domain.getAge())
                .gender(domain.getGender())
                .dateOfBirth(domain.getDateOfBirth())
                .position(domain.getPosition())
                .registrationDate(
                        domain.getRegistrationDate()
                )
                .isActive(domain.isActive())
                .build();
    }
}