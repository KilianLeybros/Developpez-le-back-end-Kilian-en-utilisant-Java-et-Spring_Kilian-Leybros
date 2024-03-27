package com.openclassrooms.chatop.controller.handler.exception;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String message){
        super(message);
    }
}
