package com.example.demo.Messages;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Messages.Requests.MessageRequest;
import com.example.demo.Messages.Responses.MessageResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/send")
    public void SaveMessage(@Payload MessageRequest request,Authentication authentication){
        messageService.SaveMessage(request,authentication);
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<MessageResponse>> getAllMessages(@PathVariable(name="id" , required = true ) String ChatId) {
        return ResponseEntity.ok(messageService.findAllChatMessages(ChatId));
    }

}