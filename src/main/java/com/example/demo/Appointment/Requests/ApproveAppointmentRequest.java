package com.example.demo.Appointment.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApproveAppointmentRequest {
    @NotNull(message = "appointment cant be null ")
    private  Integer appointmentId;
}
