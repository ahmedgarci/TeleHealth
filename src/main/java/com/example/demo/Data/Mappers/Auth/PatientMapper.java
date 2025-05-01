package com.example.demo.Data.Mappers.Auth;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Auth.Requests.RegisterPatientRequest;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Entities.Role;
import com.example.demo.Data.Repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PatientMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Patient toPatient(RegisterPatientRequest request){
      //  roleRepository.save(Role.builder().roleName("PATIENT").build());
        return Patient.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .authorities(roleRepository.findByRoleName("PATIENT"))                        
                        .username(request.getUsername())
                        .build();
    }
}