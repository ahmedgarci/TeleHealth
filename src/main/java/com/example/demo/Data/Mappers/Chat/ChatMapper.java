package com.example.demo.Data.Mappers.Chat;

import org.springframework.stereotype.Service;

import com.example.demo.Chat.Responses.ChatResponse;
import com.example.demo.Data.Entities.Chat;

@Service
public class ChatMapper {
    
    public ChatResponse toChatResponse(Chat c,Integer userId){
        return ChatResponse.builder()
                .id(c.getId())
                .lastMessage(c.getLastMessage())
                .name(c.getChatName(userId))
                .sender_Id(c.getSender().getId())
                .receiverId(c.getReceiver().getId())
                .lastMessageTime(c.getLastMessageTime())
                .build();
    }
}
