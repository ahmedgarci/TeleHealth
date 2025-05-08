package com.example.demo.Appointment;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Appointment.Requests.ApproveAppointmentRequest;
import com.example.demo.Appointment.Requests.AskForAppointmentRequest;
import com.example.demo.Appointment.Requests.CreateMeetRequest;
import com.example.demo.Appointment.Requests.DenyAppointmentRequest;
import com.example.demo.Appointment.Responses.AppointmentResponse;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Entities.AppointmentMeet;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Enums.AppointmentStatus;
import com.example.demo.Data.Mappers.Appointments.AppointmentMapper;
import com.example.demo.Data.Repositories.AppointmentMeetRepo;
import com.example.demo.Data.Repositories.AppointmentRepo;
import com.example.demo.Data.Repositories.DoctorRepo;
import com.example.demo.GlobalHandler.Exceptions.ActionNotAllowed;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;
import com.example.demo.Notifications.Notification;
import com.example.demo.Notifications.NotificationService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;    
    private final DoctorRepo doctorRepo;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentMeetRepo meetRepo;
    private final NotificationService notificationService;

    public void demandAppointment(AskForAppointmentRequest request,Authentication authentication){
            Doctor doctor = doctorRepo.findById(request.getDoctorId()).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
            var patient = (Patient)authentication.getPrincipal();
            appointmentRepo.save(appointmentMapper.toAppointment(request, patient, doctor));        
    }
    
    public void denyAppointment(DenyAppointmentRequest request,Authentication authentication){
        Appointment p = appointmentRepo.findById(request.getAppointmentId()).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
        Doctor doctor = (Doctor)authentication.getPrincipal();
        if(!p.getDoctor().getId().equals(doctor.getId())){
            throw new ActionNotAllowed("u are not included in the appointment");
        }
        p.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepo.save(p);
        notificationService.SendNotif(p.getPatient().getId(), new Notification("ur demand was denied by doctor "+doctor.getName(),false));
    }

    public void approveAppointment(ApproveAppointmentRequest request, Authentication authentication){
        Appointment p = appointmentRepo.findById(request.getAppointmentId()).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
        Doctor doctor = (Doctor)authentication.getPrincipal();
        if(!p.getDoctor().getId().equals(doctor.getId())){
            throw new ActionNotAllowed("u are not included in the appointment");
        }
        p.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepo.save(p);
        notificationService.SendNotif(p.getPatient().getId(), new Notification("ur demand was accepted by doctor "+doctor.getName(),true));
    }

    public List<AppointmentResponse> getMyPendingAppointments(Authentication authentication){
        User user = (User)authentication.getPrincipal();
        if(user instanceof Patient){
                throw new ActionNotAllowed("u need to be a doctor !");
        }
        List<Appointment> allAppointments = appointmentRepo.findByDoctorAndStatus((Doctor) user,AppointmentStatus.PENDING); 
        return allAppointments.stream().map((element)->appointmentMapper.tAppointmentResponse(element)).collect(Collectors.toList());    
    }
    
    public String CreateNewMeet(Authentication authentication,CreateMeetRequest request){
       Doctor connectedUser = (Doctor) authentication.getPrincipal();
       Appointment appointment = appointmentRepo.findById(request.getAppointmentId()).orElseThrow(()->new CustomEntityNotFoundException("appointment with id :  was not found"));
       AppointmentMeet meet = AppointmentMeet.builder().appointment(appointment).doctor(connectedUser).patient(appointment.getPatient()).meetCode(generateRandomCode()).build();        
       AppointmentMeet saved =meetRepo.save(meet);
       return saved.getMeetCode();
    }


    private String generateRandomCode(){
        String caracters = "1234567890";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom(); 
        for(int i=0;i<6;i++){
            int randomIndex = secureRandom.nextInt(caracters.length());
            codeBuilder.append(caracters.charAt(randomIndex)); 
        }
        return codeBuilder.toString();
    }
    

    


}
