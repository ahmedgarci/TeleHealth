package com.example.demo.Appointment.Requests;


import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AskForAppointmentRequest {
    @NotNull(message= "appointment date cant be null")
    @FutureOrPresent(message = "date should not be in the past")
    private LocalDate date;

    @NotNull(message= "time  cant be null")
    private LocalTime time;

    @NotBlank(message="reason is required")
    private String reason;

}
