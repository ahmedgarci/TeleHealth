package com.example.demo.Data.Mappers.Messages;

import org.springframework.stereotype.Service;

import com.example.demo.Data.Entities.Message;
import com.example.demo.Messages.Responses.MessageResponse;

@Service
public class MessageMapper {
    
    public MessageResponse toMessageResponse(Message m){
        return MessageResponse.builder()
                .id(m.getId())
                .content(m.getContent())
                .receiverId(m.getReceiverId())
                .senderId(m.getSenderId())
                .messageType(m.getMessagetype())
                .messageType(m.getMessagetype())
                //TO DO MEDIA
                .build();
    }   

}
