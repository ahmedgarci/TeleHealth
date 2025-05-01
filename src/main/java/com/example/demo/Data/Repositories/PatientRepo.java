package com.example.demo.Data.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.Patient;
@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer>{
    Optional<Patient> findByEmail(String email);
}
