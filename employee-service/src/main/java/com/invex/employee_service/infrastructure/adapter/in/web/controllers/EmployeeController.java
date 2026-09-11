package com.invex.employee_service.infrastructure.adapter.in.web.controllers;

import com.invex.employee_service.domain.exception.BussinessValidationException;
import com.invex.employee_service.domain.model.Employee;
import com.invex.employee_service.domain.model.port.in.CreateEmployeeCommand;
import com.invex.employee_service.domain.model.port.in.ManageEmployeeUseCase;
import com.invex.employee_service.domain.model.port.in.RetrieveEmployeeUseCase;
import com.invex.employee_service.domain.model.port.in.UpdateEmployeeCommand;
import com.invex.employee_service.infrastructure.adapter.in.web.dto.EmployeeRequest;
import com.invex.employee_service.infrastructure.adapter.in.web.dto.EmployeeUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(
        name = "Employees",
        description = "Operations for managing employees"
)
public class EmployeeController {

    private final ManageEmployeeUseCase manageEmployeeUseCase;
    private final RetrieveEmployeeUseCase retrieveEmployeeUseCase;

    @Operation(
            summary = "Get all employees",
            description = "Returns all employees registered in the system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Employees retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Employee.class
                            )
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(
                retrieveEmployeeUseCase.getAllEmployees()
        );
    }

    @Operation(
            summary = "Search employees by name",
            description = """
                    Searches employees by a partial name match.
                    The search may include first name, middle name,
                    paternal last name or maternal last name.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Employee.class
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid search parameter"
    )
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @Parameter(
                    description = "Partial employee name to search",
                    example = "Juan"
            )
            @RequestParam String name) {

        return ResponseEntity.ok(
                retrieveEmployeeUseCase.searchEmployeesByName(name)
        );
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Returns an employee using its unique identifier"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Employee found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = Employee.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @Parameter(
                    description = "Employee unique identifier",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                retrieveEmployeeUseCase.getEmployeeById(id)
        );
    }

    @Operation(
            summary = "Create employee",
            description = "Creates a single employee"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Employee created successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = Employee.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data"
    )
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee data to create",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = EmployeeRequest.class
                            )
                    )
            )
            @Valid
            @RequestBody EmployeeRequest request) {

        CreateEmployeeCommand command =
                toCreateCommand(request);

        Employee employee =
                manageEmployeeUseCase.createEmployee(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }

    @Operation(
            summary = "Create multiple employees",
            description = "Creates multiple employees in a single request"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Employees created successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Employee.class
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data or empty employee list"
    )
    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> createEmployeesBulk(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of employees to create",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = EmployeeRequest.class
                                    )
                            )
                    )
            )
            @RequestBody List<@Valid EmployeeRequest> requests) {

        if (requests.isEmpty()) {
            throw new BussinessValidationException(
                    "The employee list cannot be empty"
            );
        }

        List<CreateEmployeeCommand> commands =
                requests.stream()
                        .map(this::toCreateCommand)
                        .toList();

        List<Employee> employees =
                manageEmployeeUseCase.createEmployeesBulk(commands);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employees);
    }

    @Operation(
            summary = "Update employee",
            description = """
                    Updates one or more fields of an existing employee.
                    Fields not included in the request remain unchanged.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Employee updated successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = Employee.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid employee data"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @Parameter(
                    description = "Employee unique identifier",
                    example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fields to update",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = EmployeeUpdateRequest.class
                            )
                    )
            )
            @Valid
            @RequestBody EmployeeUpdateRequest request) {

        UpdateEmployeeCommand command =
                toUpdateCommand(request);

        Employee employee =
                manageEmployeeUseCase.updateEmployee(
                        id,
                        command
                );

        return ResponseEntity.ok(employee);
    }

    @Operation(
            summary = "Delete employee",
            description = "Deletes an employee using its unique identifier"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Employee deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(
                    description = "Employee unique identifier",
                    example = "1"
            )
            @PathVariable Long id) {

        manageEmployeeUseCase.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    private CreateEmployeeCommand toCreateCommand(
            EmployeeRequest request) {

        return new CreateEmployeeCommand(
                request.getFirstName(),
                request.getMiddleName(),
                request.getPaternalLastName(),
                request.getMaternalLastName(),
                request.getAge(),
                request.getGender(),
                request.getDateOfBirth(),
                request.getPosition()
        );
    }

    private UpdateEmployeeCommand toUpdateCommand(
            EmployeeUpdateRequest request) {

        return new UpdateEmployeeCommand(
                request.getFirstName(),
                request.getMiddleName(),
                request.getPaternalLastName(),
                request.getMaternalLastName(),
                request.getAge(),
                request.getGender(),
                request.getDateOfBirth(),
                request.getPosition()
        );
    }
}