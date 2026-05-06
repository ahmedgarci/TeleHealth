package com.example.demo.Data.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Entities.Patient;
@Repository
public interface PatientRepo extends JpaRepository<Patient,Integer>{
    Optional<Patient> findByEmail(String email);

    @Query(value = """
        select * from patient as p where p.id not in (select distinct sender_id from chat  Union All select distinct receiver_id from chat );
            """,nativeQuery = true)
    List<Patient> findPatientWithNoChat();
}
