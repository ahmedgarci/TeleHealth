package com.example.demo.Data.Mappers.Appointments;

import org.springframework.stereotype.Component;

import com.example.demo.Appointment.Requests.AskForAppointmentRequest;
import com.example.demo.Appointment.Responses.AppointmentResponse;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Enums.AppointmentStatus;

@Component
public class AppointmentMapper {
    
    public Appointment toAppointment(AskForAppointmentRequest request, Patient patient,Doctor doctor){
        return Appointment.builder()
                          .appointmentDate(request.getAppointmentDate())
                          .status(AppointmentStatus.PENDING)
                          .reason(request.getAppointmentReason())
                          .doctor(doctor)
                          .patient(patient)
                          .build();
    }

    public AppointmentResponse tAppointmentResponse(Appointment appointment){
        return AppointmentResponse.builder()
                                    .id(appointment.getId())
                                    .patientName(appointment.getPatient().getName())
                                    .reason(appointment.getReason())
                                    .date(appointment.getAppointmentDate())
                                    .build();

    }


}
