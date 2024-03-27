package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.CreateMessageInput;
import com.openclassrooms.chatop.model.dto.MessageResponse;
import com.openclassrooms.chatop.model.entity.MessageEntity;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.model.mapper.MessageEntityMapper;
import com.openclassrooms.chatop.repository.MessageRepository;
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

    public MessageResponse addMessage(CreateMessageInput message){
        RentalEntity rentalEntity = rentalService.findRentalById(message.rental_id());
        UserEntity userEntity = userService.findUserById(message.user_id());
        MessageEntity messageToSave = MessageEntityMapper.fromCreateMessageInput(message, userEntity, rentalEntity);

        messageRepository.save(messageToSave);
        return new MessageResponse("Message send with success");
    }
}
