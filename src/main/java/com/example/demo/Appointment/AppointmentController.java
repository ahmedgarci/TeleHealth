package com.example.demo.Appointment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Appointment.Requests.AskForAppointmentRequest;
import com.example.demo.Appointment.Requests.CreateMeetRequest;
import com.example.demo.Appointment.Responses.AppointmentResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    
    @PostMapping()
    public ResponseEntity<?> askForAppointment(@Valid @RequestBody AskForAppointmentRequest request,Authentication auth) {
        appointmentService.demandAppointment(request, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/deny/{appointmentId}")
    public ResponseEntity<?> denyAppointment(@PathVariable(name = "appointmentId",required = true) Integer appointmentId ) {
        appointmentService.denyAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/approve/{appointmentId}")
    public ResponseEntity<?> approveAppointment(@PathVariable(name = "appointmentId",required = true) Integer appointmentId) {
        appointmentService.approveAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/completed/{appointmentId}")
    public ResponseEntity<?> markAppointmentAsCompleted (@PathVariable(name = "appointmentId") Integer appointmentId) {
        appointmentService.markAppointmentAsCompleted(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/requests")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointmentsDemands() {
        return ResponseEntity.ok(appointmentService.getAppointmentDemands());
    }

    @GetMapping()
    public ResponseEntity<List<AppointmentResponse>> getAllMyConfirmedAppointments(Authentication authentication) {
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(authentication));
    }

    
 
    @PostMapping("/start")
    public ResponseEntity<?> startAppointment(Authentication auth,@Valid @RequestBody CreateMeetRequest request) {
        return ResponseEntity.ok(Map.of("roomId",appointmentService.CreateNewMeet(auth, request)));
    }





}