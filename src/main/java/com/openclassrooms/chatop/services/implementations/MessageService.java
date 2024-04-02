package com.openclassrooms.chatop.services.implementations;

import com.openclassrooms.chatop.model.dto.CreateMessageInput;
import com.openclassrooms.chatop.model.dto.MessageResponse;
import com.openclassrooms.chatop.model.entity.MessageEntity;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.model.mapper.MessageEntityMapper;
import com.openclassrooms.chatop.repository.MessageRepository;
import com.openclassrooms.chatop.services.IMessageService;
import com.openclassrooms.chatop.services.IRentalService;
import com.openclassrooms.chatop.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private IRentalService rentalService;

    @Autowired
    private IUserService userService;

    public MessageResponse addMessage(CreateMessageInput message){
        RentalEntity rentalEntity = rentalService.findRentalById(message.rental_id());
        UserEntity userEntity = userService.findUserById(message.user_id());
        MessageEntity messageToSave = MessageEntityMapper.fromCreateMessageInput(message, userEntity, rentalEntity);

        messageRepository.save(messageToSave);
        return new MessageResponse("Message send with success");
    }
}
