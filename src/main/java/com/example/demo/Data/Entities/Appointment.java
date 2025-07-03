package com.example.demo.Data.Entities;


import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.Data.Constants.AppointmentsConstants;
import com.example.demo.Data.Enums.AppointmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(
    name = AppointmentsConstants.FIND_SCHEDULED_PATIENTS,
    query = "SELECT DISTINCT p.patient FROM Appointment p WHERE p.doctor.id= :doctor_id  AND  p.status = CONFIRMED "
)
@NamedQuery(
    name =AppointmentsConstants.FIND_TODAY_APPOINTMENTS,
    query="SELECT DISTINCT p FROM Appointment p WHERE p.doctor.id = :doctor_id AND appointmentDate >= :today AND appointmentDate < :tomorrowDate AND p.status = CONFIRMED " 
)
public class Appointment {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    @OneToOne(fetch = FetchType.EAGER)
    private AppointmentMeet meet;

    private Date appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String reason;

    @CreatedDate
    private Date created_at;
    
    private Date updated_at;

}
