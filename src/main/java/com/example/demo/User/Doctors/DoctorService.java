package com.example.demo.User.Doctors;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Mappers.UserProfile.UserMapper;
import com.example.demo.Data.Repositories.DoctorRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;
import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
    
    private final DoctorRepo doctorRepo;
    private final UserMapper userMapper;

    public UserResponse getDoctorDetails(Integer doctorId){
        Doctor doctor= doctorRepo.findById(doctorId).orElseThrow(()->new CustomEntityNotFoundException("doctor was not found"));
        return userMapper.FromDoctorToUserProfileResponse(doctor);
    }

    public List<UserResponse>getAllDoctors(){
        return doctorRepo.findAll().stream().map((d)-> userMapper.FromDoctorToUserProfileResponse(d)).toList();
    }


}
