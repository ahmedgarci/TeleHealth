package com.example.demo.Chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Chat.Responses.ChatResponse;
import com.example.demo.Chat.Responses.PatientResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    
    @PostMapping
    public ResponseEntity<?> createChat( @RequestParam(name ="receiver_id" ) Integer receiverId,Authentication authentication){
        chatService.createChat(receiverId,authentication);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatsByReceiver(Authentication authentication) {
        return ResponseEntity.ok(chatService.getChatsByUserId(authentication));
    }

    @GetMapping("/available")
    public ResponseEntity<List<PatientResponse>> getPatientsWithNoChats(Authentication authentication) {
        return ResponseEntity.ok(chatService.getPatients(authentication));
    }
    


}
