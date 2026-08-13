package com.codingshuttle.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    // 1. Save a new Employee
    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        // Convert DTO (from client) -> Entity (for database)
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);

        // Save to database
        EmployeeEntity savedEntity = employeeRepository.save(toSaveEntity);

        // Convert Entity (from database) back to DTO (for client)
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    // 2. Get an Employee by ID
    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if (employeeEntity == null) return null;

        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    // 3. Get all Employees
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
