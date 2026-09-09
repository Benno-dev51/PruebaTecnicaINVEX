package com.invex.employee_service.infrastructure.adapter.in.web;

import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.ManageEmployeeUseCase;
import com.invex.employee_service.domain.model.port.in.RetrieveEmployeeUseCase;
import com.invex.employee_service.infrastructure.adapter.in.web.dto.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final ManageEmployeeUseCase manageEmployeeUseCase;
    private final RetrieveEmployeeUseCase retrieveEmployeeUseCase;

    // --- GET /employees (Todos) ---
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(retrieveEmployeeUseCase.getAllEmployees());
    }

    // --- GET /employees/search?name={name} (Búsqueda por nombre) ---
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String name) {
        return ResponseEntity.ok(retrieveEmployeeUseCase.searchEmployeesByName(name));
    }

    // --- GET /employees/{id} (Por ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(retrieveEmployeeUseCase.getEmployeeById(id));
    }

    // --- POST /employees (Uno o varios en una petición) ---
    @PostMapping
    @Transactional
    public ResponseEntity<?> createEmployees(@RequestBody List<EmployeeRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return ResponseEntity.badRequest().body("The request body cannot be empty");
        }

        if (requests.size() == 1) {
            // Si viene 1 solo empleado, usamos el caso de uso individual
            Employee employee = buildEmployeeFromRequest(requests.get(0));
            Employee savedEmployee = manageEmployeeUseCase.createEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } else {
            // Si vienen varios, mapeamos toda la lista y usamos el bulk
            List<Employee> employeesToSave = requests.stream()
                    .map(this::buildEmployeeFromRequest)
                    .collect(Collectors.toList());
            List<Employee> savedEmployees = manageEmployeeUseCase.createEmployeesBulk(employeesToSave);
            return new ResponseEntity<>(savedEmployees, HttpStatus.CREATED);
        }
    }

    // --- PUT /employees/{id} (Actualiza todos o algunos campos) ---
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        Employee employeeData = buildEmployeeFromRequest(request);
        // Pasamos true para isPartialUpdate, además nuestro dominio ya ignora los valores null por diseño
        Employee updatedEmployee = manageEmployeeUseCase.updateEmployee(id, employeeData, true);
        return ResponseEntity.ok(updatedEmployee);
    }

    // --- DELETE /employees/{id} ---
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        manageEmployeeUseCase.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // --- Método Auxiliar ---
    private Employee buildEmployeeFromRequest(EmployeeRequest request) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .paternalLastName(request.getPaternalLastName())
                .maternalLastName(request.getMaternalLastName())
                .age(request.getAge())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .position(request.getPosition())
                .build();
    }
}