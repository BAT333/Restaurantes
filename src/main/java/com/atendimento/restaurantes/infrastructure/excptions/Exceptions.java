package com.atendimento.restaurantes.infrastructure.excptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Exceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity ex(MethodArgumentNotValidException ex){
        var erros =  ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(MensagemErros::new).toList());

    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity erroRegister(){
        return ResponseEntity.status(203).body("ERROR WHEN REGISTERING, THE USER PROBABLY ALREADY EXISTS");
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity erro400(){
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity id(){
        return ResponseEntity.notFound().build();
    }

    public record MensagemErros(String field, String description){
        public MensagemErros(FieldError error){
            this(error.getField(),error.getDefaultMessage());
        }

    }

}
