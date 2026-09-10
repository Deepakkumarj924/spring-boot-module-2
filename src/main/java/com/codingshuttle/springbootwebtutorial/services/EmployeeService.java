package com.codingshuttle.springbootwebtutorial.services;

import com.codingshuttle.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.springbootwebtutorial.exceptions.ResourceNotFoundException;
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

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity toSaveEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity savedEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    public boolean isEmployeeExist(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return exists;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        isEmployeeExist(id);

        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        isEmployeeExist(id);

        modelMapper.map(employeeDTO, employeeEntity);
        employeeEntity.setId(id);
        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id) {
        isEmployeeExist(id);
        employeeRepository.deleteById(id);
        return true;

    }

    public EmployeeDTO updatePartialEmployeeById(Long id, java.util.Map<String, Object> updates) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        isEmployeeExist(id);

        updates.forEach((field, value) -> {
            // 1. Fix: Search inside EmployeeEntity.class, not EmployeeDTO.class
            java.lang.reflect.Field fieldToBeUpdated = org.springframework.util.ReflectionUtils.findField(EmployeeEntity.class, field);

            // 2. Fix: Add a null check to prevent app crashes if a bad field name is sent
            if (fieldToBeUpdated != null) {
                fieldToBeUpdated.setAccessible(true);
                org.springframework.util.ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
            }
        });

        // 3. Fix: Use a new variable name (savedEntity) so employeeEntity remains "effectively final"
        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEntity, EmployeeDTO.class);
    }



}
