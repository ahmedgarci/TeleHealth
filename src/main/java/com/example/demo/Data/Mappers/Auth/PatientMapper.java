package com.example.demo.Data.Mappers.Auth;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Auth.Requests.RegisterPatientRequest;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Enums.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PatientMapper {

    private final PasswordEncoder passwordEncoder;

    public Patient toPatient(RegisterPatientRequest request){
        return Patient.builder()
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.PATIENT)
                        .username(request.username())
                        .build();
    }
}