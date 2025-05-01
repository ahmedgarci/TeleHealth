package com.example.demo.Data.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.Doctor;
@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
    Optional<Doctor> findByEmail(String email);

}
