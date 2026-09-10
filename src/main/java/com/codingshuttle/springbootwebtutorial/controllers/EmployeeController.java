package com.codingshuttle.springbootwebtutorial.controllers;


import com.codingshuttle.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeDTO = Optional.ofNullable(employeeService.getEmployeeById(id));
        return employeeDTO.map(employeeDTO1 ->  ResponseEntity.ok().body(employeeDTO1))
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found"));

    }



    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@Valid @RequestBody EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED) ;
    }

    // 4. Update an Employee (PUT)
    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long id) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(id, employeeDTO);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // 5. Delete an Employee (DELETE)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id) {
        boolean gotDeleted = employeeService.deleteEmployeeById(id);
        if (!gotDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }

    // 6. Partially Update an Employee (PATCH)
    @PatchMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody @Valid java.util.Map<String, Object> updates, @PathVariable Long id) {
        EmployeeDTO updatedEmployee = employeeService.updatePartialEmployeeById(id, updates);
        if (updatedEmployee == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedEmployee);
    }
}
