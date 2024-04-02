package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.CreateMessageInput;
import com.openclassrooms.chatop.model.dto.MessageResponse;

public interface IMessageService {

    MessageResponse addMessage(CreateMessageInput message);
}
