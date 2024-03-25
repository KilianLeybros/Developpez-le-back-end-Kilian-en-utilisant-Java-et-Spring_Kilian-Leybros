package com.openclassrooms.chatop.controller.handler.exception;

public class InvalidFileFormatException extends RuntimeException{

    public InvalidFileFormatException(String message){
        super(message);
    }
}
