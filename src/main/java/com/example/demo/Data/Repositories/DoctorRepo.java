package com.example.demo.Data.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.Doctor;

@Repository
public interface DoctorRepo extends MongoRepository<Doctor,String> {
    Optional<Doctor> findByEmail(String email);

}
