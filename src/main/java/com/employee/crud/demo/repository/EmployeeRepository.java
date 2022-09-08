package com.employee.crud.demo.repository;

import com.employee.crud.demo.model.EmployeeDao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<EmployeeDao,String> {
}
