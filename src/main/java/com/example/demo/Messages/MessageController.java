package com.example.demo.Messages;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Messages.Requests.MessageRequest;
import com.example.demo.Messages.Responses.MessageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/send")
    public void SaveMessage(@Payload MessageRequest request){
        messageService.SaveMessage(request);
    }


    @GetMapping("/chat/{id}")
    public ResponseEntity<List<MessageResponse>> getAllMessages(@PathVariable("id") String ChatId) {
        return ResponseEntity.ok(messageService.findAllChatMessages(ChatId));
    }

}