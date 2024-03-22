package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.CreateMessageInput;
import com.openclassrooms.chatop.model.dto.ResponseMessage;
import com.openclassrooms.chatop.model.entity.MessageEntity;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.repository.MessageRepository;
import com.openclassrooms.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    public ResponseMessage addMessage(CreateMessageInput message){
        RentalEntity rentalEntity = rentalService.findRentalById(message.rental_id());
        UserEntity userEntity = userService.findUserById(message.user_id());
        MessageEntity messageToSave = new MessageEntity()
                .setMessage(message.message())
                .setCreatedAt(Timestamp.from(Instant.now()))
                .setRental(rentalEntity)
                .setUser(userEntity);

        messageRepository.save(messageToSave);
        return new ResponseMessage("Message send with success");
    }
}
