package com.example.demo.Data.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Constants.ChatConstants;
import com.example.demo.Data.Entities.Chat;

@Repository
public interface ChatRepo extends JpaRepository<Chat,String>{
    @Query(name=ChatConstants.FIND_CHAT_BY_USER_ID)
    List<Chat> getChatsByUserId(@Param("userId") Integer userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDERID_AND_RECEIVERID)
    Optional<Chat> findChatByReceiverAndSenderId(@Param("senderId")Integer senderId,@Param("receiverId") Integer receiverId);

        
}