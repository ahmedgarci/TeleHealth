package com.example.demo.Data.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Data.Constants.AppointmentsConstants;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Enums.AppointmentStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class AppointmentEntityManager {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Appointment> getTodayAppointments(){
        
        LocalDate localDate = LocalDate.now();

        TypedQuery<Appointment> queryy = entityManager.createNamedQuery(AppointmentsConstants.FIND_APPOINTMENTS_BY_STATUS, Appointment.class)

        .setParameter("status", AppointmentStatus.CANCELLED)

        .setParameter("today",localDate)

        .setParameter("tomorrowDate",localDate.plusDays(1));
    
        return queryy.getResultList();
    }
}
