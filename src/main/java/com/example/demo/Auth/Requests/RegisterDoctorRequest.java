package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegisterDoctorRequest extends RegisterPatientRequest {
    @NotBlank(message = "specialization is required")
    private String specialization;
    @NotNull(message = "experience cant be null")
    private int yearsOfExperience;
    
}