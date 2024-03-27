package com.openclassrooms.chatop.model.mapper;

import com.openclassrooms.chatop.model.dto.CreateMessageInput;
import com.openclassrooms.chatop.model.entity.MessageEntity;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;


public class MessageEntityMapper {

    public static MessageEntity fromCreateMessageInput(CreateMessageInput messageInput, UserEntity user, RentalEntity rental){
        return new MessageEntity()
                .setMessage(messageInput.message())
                .setCreatedAt(Timestamp.from(Instant.now()))
                .setRental(rental)
                .setUser(user);
    }
}
