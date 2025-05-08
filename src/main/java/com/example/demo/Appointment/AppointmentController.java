package com.example.demo.Appointment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Appointment.Requests.ApproveAppointmentRequest;
import com.example.demo.Appointment.Requests.AskForAppointmentRequest;
import com.example.demo.Appointment.Requests.CreateMeetRequest;
import com.example.demo.Appointment.Requests.DenyAppointmentRequest;
import com.example.demo.Appointment.Responses.AppointmentResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    
    @PostMapping("/demand")
    public ResponseEntity<?> askForAppointment(@Valid @RequestBody AskForAppointmentRequest request,Authentication auth) {
        appointmentService.demandAppointment(request, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/deny")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> denyAppointment(@Valid @RequestBody DenyAppointmentRequest request,Authentication auth) {
        appointmentService.denyAppointment(request, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/approve")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> approveAppointment(@Valid @RequestBody ApproveAppointmentRequest request,Authentication auth) {
        appointmentService.approveAppointment(request, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/today")
    public ResponseEntity<List<AppointmentResponse>> getAllMyAppointments(Authentication authentication) {
        return ResponseEntity.ok(appointmentService.getMyPendingAppointments(authentication));
    }

 
    @PostMapping("/start")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> startAppointment(Authentication auth,@Valid @RequestBody CreateMeetRequest request) {
        System.out.println(request.getAppointmentId());
        return ResponseEntity.ok(appointmentService.CreateNewMeet(auth, request));
    }





}