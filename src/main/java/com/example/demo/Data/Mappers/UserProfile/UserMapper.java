package com.example.demo.Data.Mappers.UserProfile;

import org.springframework.stereotype.Component;

import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Photo.PhotoService;
import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PhotoService photoService;

    public UserResponse FromDoctorToUserProfileResponse(Doctor doctor){  
        return UserResponse.builder()
                                    .id(doctor.getId())
                                    .email(doctor.getEmail())
                                    .username(doctor.getName())
                                    .specialization(doctor.getSpecialization())
                                    .yearsOfExprerience(doctor.getYearsOfExperience())
                                    .Longtitude(doctor.getLongtitude())
                                    .altitude(doctor.getLattitude())
                                    .description(doctor.getDescription())
                                    .photo(photoService.readFileFromLocation(doctor.getPhoto()))
                                    .build();
    }

    public UserResponse FromPatientToUserProfileResponse(Patient patient){  
        return UserResponse.builder()
                                    .email(patient.getEmail())
                                    .username(patient.getName())
                                    .build();
    }

    
}
