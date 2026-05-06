package com.example.demo.Appointment.Responses;

import java.time.LocalTime;

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
public class AppointmentResponse {
    private Integer id;
    private LocalTime time;
    private String meetCode;
    private String patientPhone;
    private String patientName;
    private String reason;

}