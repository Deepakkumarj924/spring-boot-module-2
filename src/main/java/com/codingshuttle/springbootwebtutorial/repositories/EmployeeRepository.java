package com.codingshuttle.springbootwebtutorial.repositories;

import com.codingshuttle.springbootwebtutorial.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;



@Repository
public interface EmployeeRepository extends jpaRepository<EmployeeEntity, Long> {
}
