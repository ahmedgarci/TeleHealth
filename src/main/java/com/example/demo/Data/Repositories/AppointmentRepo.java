package com.example.demo.Data.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Constants.AppointmentsConstants;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Enums.AppointmentStatus;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Integer>{
    
    List<Appointment> findByStatus(AppointmentStatus status);

    @Query(name = AppointmentsConstants.FIND_APPOINTMENTS_BY_STATUS)
    List<Appointment> findAppointmentsByStatus(AppointmentStatus status);
   

}