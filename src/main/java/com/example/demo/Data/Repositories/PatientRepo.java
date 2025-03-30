package com.example.demo.Data.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.Patient;
@Repository
public interface PatientRepo extends MongoRepository<Patient,String>{
    Optional<Patient> findByEmail(String email);
}
