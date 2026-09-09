package com.invex.employee_service.infrastructure.adapter.out.persistence.repository;

import com.invex.employee_service.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.paternalLastName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.maternalLastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<EmployeeEntity> findByPartialName(@Param("name") String name);
}