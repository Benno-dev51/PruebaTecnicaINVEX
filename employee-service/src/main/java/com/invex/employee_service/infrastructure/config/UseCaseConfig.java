package com.invex.employee_service.infrastructure.config;

import com.invex.employee_service.aplication.service.ManageEmployeeService;
import com.invex.employee_service.aplication.service.RetrieveEmployeeService;
import com.invex.employee_service.domain.model.port.in.ManageEmployeeUseCase;
import com.invex.employee_service.domain.model.port.in.RetrieveEmployeeUseCase;
import com.invex.employee_service.domain.model.port.out.EmployeeRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Clock;

@Configuration
@EnableTransactionManagement
public class UseCaseConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public RetrieveEmployeeUseCase retrieveEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort) {
        return new RetrieveEmployeeService(employeeRepositoryPort);
    }

    @Bean
    public ManageEmployeeUseCase manageEmployeeUseCase(EmployeeRepositoryPort employeeRepositoryPort, Clock clock) {
        return new ManageEmployeeService(employeeRepositoryPort, clock);
    }
}
