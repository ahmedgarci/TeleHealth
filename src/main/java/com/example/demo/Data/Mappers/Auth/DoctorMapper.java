package com.example.demo.Data.Mappers.Auth;


import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Auth.Requests.RegisterDoctorRequest;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    
    public Doctor toDoctor(RegisterDoctorRequest request){
        return Doctor.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .username(request.getUsername())
                        .authorities(List.of(roleRepository.findByRoleName("DOCTOR")))
                        .yearsOfExperience(request.getYearsOfExperience())
                        .specialization(request.getSpecialization())
                        .location(request.getPlace())
                        .build();
    }
}
