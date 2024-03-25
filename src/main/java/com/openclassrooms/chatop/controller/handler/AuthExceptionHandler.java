package com.openclassrooms.chatop.controller.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.hibernate.persister.collection.mutation.UpdateRowsCoordinatorOneToMany;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class AuthExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadCredentialException(BadCredentialsException exception){
        return "L'adresse mail ou le mot de passe est incorrect";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAccessDeniedException(AccessDeniedException exception){
        return "Vous n'êtes pas authorisé";
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleSignatureException(SignatureException exception){
        return "Signature du token invalide";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleExpiredJwtException(ExpiredJwtException exception){
        return "JWT expiré";
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleException(AuthenticationException exception){
        return "JWT expiré";
    }



}
