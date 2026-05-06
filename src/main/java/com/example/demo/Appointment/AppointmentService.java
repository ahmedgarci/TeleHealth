package com.example.demo.Appointment;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Appointment.Requests.AskForAppointmentRequest;
import com.example.demo.Appointment.Requests.CreateMeetRequest;
import com.example.demo.Appointment.Responses.AppointmentResponse;
import com.example.demo.Data.Entities.Appointment;
import com.example.demo.Data.Entities.AppointmentMeet;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Enums.AppointmentStatus;
import com.example.demo.Data.Mappers.Appointments.AppointmentMapper;
import com.example.demo.Data.Repositories.AppointmentEntityManager;
import com.example.demo.Data.Repositories.AppointmentMeetRepo;
import com.example.demo.Data.Repositories.AppointmentRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;
import com.example.demo.Notifications.NotificationService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepo appointmentRepo;    
    private final AppointmentMapper appointmentMapper;
    private final AppointmentMeetRepo meetRepo;
    private final NotificationService notificationService;
    private final AppointmentEntityManager appointmentEntityManager;


    public void demandAppointment(AskForAppointmentRequest request,Authentication authentication){
            var patient = (Patient)authentication.getPrincipal();
            appointmentRepo.save(appointmentMapper.toAppointment(request, patient));        
    }
    
    public void markAppointmentAsCompleted(@NotNull Integer appointmentId){
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(appointment);
    }


    public void denyAppointment(Integer appointmentId){
        Appointment p = appointmentRepo.findById(appointmentId).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
  //      Doctor doctor = (Doctor)authentication.getPrincipal();
        p.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepo.save(p);
  //      notificationService.SendNotif(p.getPatient().getId(), new Notification("ur demand was denied by doctor "+doctor.getName(),false));
    }

    public void approveAppointment(Integer appointmentId){
        Appointment p = appointmentRepo.findById(appointmentId).orElseThrow(()->new CustomEntityNotFoundException("doctor not found"));
//        Doctor doctor = (Doctor)authentication.getPrincipal();
        p.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepo.save(p);
//        notificationService.SendNotif(p.getPatient().getId(), new Notification("ur demand was accepted by doctor ",true));
    }

    public List<AppointmentResponse> getAppointmentDemands() {
//        BaseUser user = (BaseUser) authentication.getPrincipal();
    
        List<Appointment> pendingAppointments = appointmentRepo.findByStatus(AppointmentStatus.PENDING);
            return pendingAppointments.stream()
                    .map(appointmentMapper::toAppointmentResponse)
                    .collect(Collectors.toList());
    }
    
    public List<AppointmentResponse> getMyConfirmedAppointments(Authentication authentication) {
        List<Appointment> confirmedAppointments = appointmentEntityManager.getTodayAppointments();
        return confirmedAppointments.stream()
                .map(appointmentMapper::toAppointmentResponse)
                .collect(Collectors.toList());
        }
    

    public String CreateNewMeet(Authentication authentication,CreateMeetRequest request){
       Appointment appointment = appointmentRepo.findById(request.getAppointmentId())
        .orElseThrow(()->new CustomEntityNotFoundException("appointment with id :  was not found"));
       AppointmentMeet meet = AppointmentMeet.builder().appointment(appointment).patient(appointment.getPatient()).meetCode(generateRandomCode()).build();        
       AppointmentMeet saved =meetRepo.save(meet);
       appointment.setMeet(saved);
       appointmentRepo.save(appointment);
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
