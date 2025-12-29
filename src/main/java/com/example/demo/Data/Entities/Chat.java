package com.example.demo.Data.Entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Data.Constants.ChatConstants;
import com.example.demo.Data.Enums.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDERID_AND_RECEIVERID,
 query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId AND c.receiver.id = :receiverId")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_USER_ID,
 query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :userId OR c.receiver.id = :userId")
@EntityListeners(AuditingEntityListener.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    private List<Message> messages;

    @Transient
    public String getChatName(Integer senderId) {      
        if (sender.getId().equals(senderId)) {
            return receiver.getName();
        }
        return sender.getName();
    }

    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(messages.size()-1).getContent();
        }
        return null;

    }
    @Transient
    public LocalDateTime getLastMessageTime(){
        if(messages != null && !messages.isEmpty() ){
            return messages.get(0).getCreated_at();
        }
        return null;
    }

}
