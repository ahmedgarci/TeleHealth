package com.example.demo.Data.Mappers.Chat;

import org.springframework.stereotype.Component;

import com.example.demo.Chat.Responses.ChatResponse;
import com.example.demo.Data.Entities.Chat;

@Component
public class ChatMapper {
    
    public ChatResponse toChatResponse(Chat c,Integer connectedUser){
        return ChatResponse.builder()
                .id(c.getId())
                .lastMessage(c.getLastMessage())
                .name(c.getReceiver().getId() != connectedUser ? c.getReceiver().getName() : c.getSender().getName())
                .receiverId(c.getReceiver().getId() != connectedUser ? c.getReceiver().getId() : c.getSender().getId())
                .lastMessageTime(c.getLastMessageTime())
                .build();
    }
}
