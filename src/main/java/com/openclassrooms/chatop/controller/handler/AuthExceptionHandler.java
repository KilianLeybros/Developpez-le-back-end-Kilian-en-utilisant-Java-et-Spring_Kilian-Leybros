package com.openclassrooms.chatop.controller.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.hibernate.persister.collection.mutation.UpdateRowsCoordinatorOneToMany;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleAuthException(Exception exception){
        ProblemDetail problemDetail = null;

        if (exception instanceof BadCredentialsException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            problemDetail.setProperty("description", "L'adresse mail ou le mot de passe est incorrect");

            return problemDetail;
        }


        if (exception instanceof AccessDeniedException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            problemDetail.setProperty("description", "Vous n'êtes pas authorisé");
        }

        if (exception instanceof SignatureException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            problemDetail.setProperty("description", "Signature du token invalide");
        }

        if (exception instanceof ExpiredJwtException) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            problemDetail.setProperty("description", "JWT expiré");
        }

        if (problemDetail == null) {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            problemDetail.setProperty("description", "Erreur serveur");
        }

        return problemDetail;
    }
}
