package com.example.demo.Appointment.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DenyAppointmentRequest {
        @NotNull(message = "appointment id can t be null")
        private Integer appointmentId;
}
