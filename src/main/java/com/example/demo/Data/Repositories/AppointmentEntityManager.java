package com.example.demo.Data.Repositories;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Data.Constants.AppointmentsConstants;
import com.example.demo.Data.Entities.Appointment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class AppointmentEntityManager {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Appointment> getTodayAppointmentsUsingDoctorId(Integer doctorId){
        LocalDate localDate = LocalDate.now();

        Date todayStart = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date tomorrowDate = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        TypedQuery<Appointment> queryy = entityManager.createNamedQuery(AppointmentsConstants.FIND_TODAY_APPOINTMENTS, Appointment.class)

        .setParameter("doctor_id", doctorId)

        .setParameter("today",todayStart)

        .setParameter("tomorrowDate",tomorrowDate);
        

        return queryy.getResultList();
    }
}
