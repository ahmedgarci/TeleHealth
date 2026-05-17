package com.example.demo.Data.Mappers.Appointments;


import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.Appointment.Requests.AskForAppointmentRequest;
import com.example.demo.Appointment.Responses.AppointmentResponse;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Enums.AppointmentStatus;

@Component
public class AppointmentMapper {
    
    public Appointment toAppointment(AskForAppointmentRequest request, User patient){
        return Appointment.builder()
                          .status(AppointmentStatus.PENDING)
                          .date(request.getDate())
                          .time(request.getTime())
                          .reason(request.getReason())
                          .patient(patient)
                          .build();
    }

    public AppointmentResponse toAppointmentResponse(Appointment appointment){
        return AppointmentResponse.builder()
                                    .id(appointment.getId())
                                    .patientName(appointment.getPatient().getName())
                                    .time(appointment.getTime())
                                    .reason(appointment.getReason())
                                    .patientPhone("96062103")
                                    .meetCode(Optional.ofNullable(appointment.getMeet()).map((m)-> m.getMeetCode()).orElse(null))
                                    .status(appointment.getStatus().toString())
                                    .build();

    }

}
