package com.codingshuttle.springbootwebtutorial.dto;

import com.codingshuttle.springbootwebtutorial.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "name of employee cannot be blank")
    private String name;

    @NotBlank(message = "Email of the employee cannot be blank")
    @Email(message = "Email should be a valid email format")
    private String email;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age of Employee cannot be less than 18")
    @Max(value = 80, message = "Age of Employee cannot be greater than 80")
    private Integer age;

    @PastOrPresent(message = "Date of joining cannot be in the future")
    private LocalDate dateOfJoining;

    private Boolean isActive;

    @NotBlank(message = "Role of the employee cannot be blank")
    @EmployeeRoleValidation
    private String role;

    @NotNull(message = "Salary of Employee should be not null")
    @Positive(message = "Salary of Employee should be positive")
    private Double salary;
}
