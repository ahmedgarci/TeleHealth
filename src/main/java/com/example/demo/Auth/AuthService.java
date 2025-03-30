package com.example.demo.Auth;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.Auth.Requests.AuthenticateRequest;
import com.example.demo.Auth.Requests.RegisterDoctorRequest;
import com.example.demo.Auth.Requests.RegisterPatientRequest;
import com.example.demo.Auth.Responses.AuthenticationResponse;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Mappers.Auth.DoctorMapper;
import com.example.demo.Data.Mappers.Auth.PatientMapper;
import com.example.demo.Data.Repositories.DoctorRepo;
import com.example.demo.Data.Repositories.PatientRepo;
import com.example.demo.Security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final JwtService jwtService;
    public void RegisterPatient(RegisterPatientRequest request){
        Patient patient = patientMapper.toPatient(request);
        patientRepo.save(patient);
    }
    public void RegisterDoctor(RegisterDoctorRequest request){
        Doctor doctor = doctorMapper.toDoctor(request);
        doctorRepo.save(doctor);
    }

    public AuthenticationResponse LoginUser(AuthenticateRequest request){
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails user = (User)authentication.getPrincipal();
        return AuthenticationResponse.builder().Jwt(jwtService.generateToken(user)).build();
    }

    public void LogoutUser(){
        
    }
    

}
