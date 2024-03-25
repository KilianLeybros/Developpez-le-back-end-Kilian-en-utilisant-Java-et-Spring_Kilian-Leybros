package com.openclassrooms.chatop.controller.handler;

import com.openclassrooms.chatop.controller.handler.exception.InvalidFileFormatException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> requestBodyNotValidHandler(MethodArgumentNotValidException exception){
        Map<String, String> errorResult = new HashMap<String, String>();
        exception.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResult.put(fieldName, errorMessage);
        });
        return errorResult;
    }



    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundHandler(EntityNotFoundException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String ioExceptionHandler(IOException exception){
        return "Une erreur est survenue lors de l'écriture/lecture d'un fichier";
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidFileFormatHandler(InvalidFileFormatException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String fileNotFoundHandler(FileNotFoundException exception){
        return exception.getMessage();
    }

}
