package com.example.demo.Data.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Constants.MessageConstants;
import com.example.demo.Data.Entities.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long>{
    
    @Query(name = MessageConstants.FindMessagesByChatId)
    List<Message> findMessagesByChatId(@Param("ChatId") String chatId);
    
}
