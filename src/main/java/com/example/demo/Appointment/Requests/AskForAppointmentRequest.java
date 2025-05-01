package com.example.demo.Appointment.Requests;


import java.util.Date;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AskForAppointmentRequest {
    @NotNull(message = "doctor id can t be null")
    private Integer doctorId;
    @NotNull(message= "appointment date cant be null")
    @FutureOrPresent(message = "date should not be in the past")
    private Date appointmentDate;
    @NotBlank(message="reason is required")
    private String appointmentReason;

}
