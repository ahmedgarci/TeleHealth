package com.example.demo.Data.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Constants.AppointmentsConstants;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Enums.AppointmentStatus;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer>{
    
    List<Appointment> findByDoctorAndStatus(Doctor doctor,AppointmentStatus status);
    
    @Query(name = AppointmentsConstants.FIND_SCHEDULED_PATIENTS)
    List<Patient> findDoctorScheduledPatients(@Param("doctor_id") Integer doctor_id);
}
