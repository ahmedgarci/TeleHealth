package com.example.demo.User.Profile;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Mappers.Profile.UserProfileMapper;
import com.example.demo.Data.Repositories.UserRepository;
import com.example.demo.User.Profile.Responses.UserResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
 @RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserProfileMapper userMapper;

    public UserResponse getUserInfo(Authentication authentication){
            User patient = (User)authentication.getPrincipal();
            User patientEntity = userRepository.findById(patient.getId()).orElseThrow(()->new EntityNotFoundException("user not found"));
            return userMapper.toProfileResponse(patientEntity);
        }
    

    



}
