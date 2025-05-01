package com.example.demo.User.Profile;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Mappers.UserProfile.UserMapper;
import com.example.demo.Data.Repositories.DoctorRepo;
import com.example.demo.Data.Repositories.PatientRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;
import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final UserMapper userMapper;

    public UserResponse getUserInfo(Authentication authentication){
        if(authentication.getPrincipal() instanceof Doctor ){
            Doctor doctor = (Doctor)authentication.getPrincipal();
            Doctor doctorEntity = doctorRepo.findById(doctor.getId()).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
            return userMapper.FromDoctorToUserProfileResponse(doctorEntity);
        }
        else{
            Patient patient = (Patient)authentication.getPrincipal();
            Patient patientEntity = patientRepo.findById(patient.getId()).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
            return userMapper.FromPatientToUserProfileResponse(patientEntity);
        }
    }

    



}
