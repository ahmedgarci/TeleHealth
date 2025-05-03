package com.example.demo.User.Doctors;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;    

    @GetMapping("/{doctorId}")
    public ResponseEntity<UserResponse> getSpecificDoctorDetails(@PathVariable("doctorId") Integer doctorId){
        return ResponseEntity.ok(doctorService.getDoctorDetails(doctorId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

}