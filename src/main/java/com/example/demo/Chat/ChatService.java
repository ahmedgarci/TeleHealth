package com.example.demo.Chat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.example.demo.Chat.Responses.ChatResponse;
import com.example.demo.Chat.Responses.PatientResponse;
import com.example.demo.Data.Entities.Chat;
import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Mappers.Chat.ChatMapper;
import com.example.demo.Data.Repositories.ChatRepo;
import com.example.demo.Data.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepo chatRepo;    
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    public List<ChatResponse> getChatsByUserId(Authentication currentUser){
        User user = (User)currentUser.getPrincipal();
        return chatRepo.getChatsByUserId(user.getId()).stream().map((c)->chatMapper.toChatResponse(c, user.getId())).collect(Collectors.toList());
    }

    public void createChat(Integer receiverId,Authentication authentication){
        User connectedUser = (User)authentication.getPrincipal();
        User receiver = userRepository.findById(receiverId).orElseThrow(()->new EntityNotFoundException("receiver with id : "+receiverId+ "was not found"));
        Optional<Chat> chat = chatRepo.findChatByReceiverAndSenderId(connectedUser.getId(),receiverId);
        if(chat.isPresent())return;              
        Chat c = new Chat();
        c.setReceiver(receiver);
        c.setSender(connectedUser);
        chatRepo.save(c);
    }

    public List<PatientResponse> getPatients(Authentication authentication){
        List<User> patients = userRepository.findPatientWithNoChat();
        if(patients.isEmpty()){
            return List.of();
        }
        return patients.stream().map((p)->new PatientResponse(p.getId(),p.getName())).toList();
    }


}
