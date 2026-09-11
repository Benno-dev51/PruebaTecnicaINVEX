package com.invex.employee_service.infrastructure.adapter.out.persistence;

import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import com.invex.employee_service.infrastructure.adapter.out.persistence.entity.EmployeeEntity;
import com.invex.employee_service.infrastructure.adapter.out.persistence.mapper.EmployeePersistenceMapper;
import com.invex.employee_service.infrastructure.adapter.out.persistence.repository.SpringDataEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeePersistenceAdapter
        implements EmployeeRepositoryPort {

    private final SpringDataEmployeeRepository repository;
    private final EmployeePersistenceMapper mapper;

    @Override
    public Employee save(Employee employee) {

        EmployeeEntity entity =
                mapper.toEntity(employee);

        EmployeeEntity savedEntity =
                repository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Employee> saveAll(
            List<Employee> employees) {

        List<EmployeeEntity> entities =
                employees.stream()
                        .map(mapper::toEntity)
                        .toList();

        return repository.saveAll(entities)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Employee> findById(Long id) {

        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Employee> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Employee> findByPartialName(
            String name) {

        return repository.findByPartialName(name)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}