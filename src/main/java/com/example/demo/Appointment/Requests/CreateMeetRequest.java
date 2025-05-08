package com.example.demo.Appointment.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMeetRequest {
    @NotNull(message = "appointment id is required")
    private Integer appointmentId;    
}
