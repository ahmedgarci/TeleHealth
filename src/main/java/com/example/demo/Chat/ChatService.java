package com.example.demo.Chat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.example.demo.Chat.Responses.ChatResponse;
import com.example.demo.Chat.Responses.ScheduledPatients;
import com.example.demo.Data.Entities.Chat;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Mappers.Chat.ChatMapper;
import com.example.demo.Data.Repositories.AppointmentRepo;
import com.example.demo.Data.Repositories.ChatRepo;
import com.example.demo.Data.Repositories.UserRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepo chatRepo;    
    private final ChatMapper chatMapper;
    private final UserRepo userRepo;    
    private final AppointmentRepo appointmentRepo;

    public List<ChatResponse> getChatsByUserId(Authentication currentUser){
        User user = (User)currentUser.getPrincipal();
        return chatRepo.getChatsByUserId(user.getId()).stream().map((c)->chatMapper.toChatResponse(c, user.getId())).collect(Collectors.toList());
    }

    public String createChat(Integer senderId,Integer receiverId){
         Optional<Chat> chat = chatRepo.findChatByReceiverAndSenderId(senderId,receiverId);
         if(chat.isPresent()){
             return chat.get().getId();
         }
        User sender = userRepo.findById(senderId).orElseThrow(()->new CustomEntityNotFoundException("sender with id : "+senderId+ "was not found"));
        User receiver = userRepo.findById(receiverId).orElseThrow(()->new CustomEntityNotFoundException("receiver with id : "+receiverId+ "was not found"));
         Chat c = new Chat();
         c.setReceiver(receiver);
         c.setSender(sender);
         Chat savedChat = chatRepo.save(c);
         return savedChat.getId();
    }

    public List<ScheduledPatients> getPatients(Authentication authentication){
        Doctor doctor= (Doctor)authentication.getPrincipal();
        List<Patient> patients = appointmentRepo.findDoctorScheduledPatients(doctor.getId());
        if(patients.isEmpty()){
            return null;
        }
        return patients.stream().map((p)->new ScheduledPatients(p.getId(),p.getName(),p.getUsername())).toList();

    }


}
