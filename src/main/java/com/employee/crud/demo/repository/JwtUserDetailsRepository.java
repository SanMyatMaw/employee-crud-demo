package com.employee.crud.demo.repository;

import com.employee.crud.demo.model.UserDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtUserDetailsRepository extends MongoRepository<UserDao,String> {

    UserDao findByUsername(String username);
}
