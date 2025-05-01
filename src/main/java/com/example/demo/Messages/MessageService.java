package com.example.demo.Messages;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.Data.Entities.Chat;
import com.example.demo.Data.Entities.Message;
import com.example.demo.Data.Enums.MessageState;
import com.example.demo.Data.Mappers.Messages.MessageMapper;
import com.example.demo.Data.Repositories.ChatRepo;
import com.example.demo.Data.Repositories.MessageRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;
import com.example.demo.Messages.Requests.MessageRequest;
import com.example.demo.Messages.Responses.MessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatRepo chatRepo;
    private final MessageRepo messageRepo;
    private final MessageMapper mapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void SaveMessage(MessageRequest request){
        Chat chat = chatRepo.findById(request.getChatId()).orElseThrow(()->new CustomEntityNotFoundException("chat with id :"+ request.getChatId()+ "was not found"));
        Message message = Message.builder()
                             .content(request.getContent())
                             .chat(chat)
                             .messageState(MessageState.SENT)
                             .senderId(request.getSenderId())
                             .receiverId(request.getReceiverId())
                             .Messagetype(request.getMessageType())   
                            .build();
        messageRepo.save(message);
        simpMessagingTemplate.convertAndSendToUser(request.getReceiverId().toString(), "/messages", mapper.toMessageResponse(message));

    }

    public List<MessageResponse> findAllChatMessages(String chatId){
        return messageRepo.findMessagesByChatId(chatId)
            .stream().map((m)->mapper.toMessageResponse(m)).toList();
    }



}
