package com.example.demo.Data.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Data.Entities.User;

public interface UserRepo  extends MongoRepository<User,String>{
    Optional<User> findByEmail(String email); 
}
