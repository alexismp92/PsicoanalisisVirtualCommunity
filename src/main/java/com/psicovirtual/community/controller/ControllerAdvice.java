package com.psicovirtual.community.controller;

import com.psicovirtual.community.dto.error.ErrorMessage;
import com.psicovirtual.community.exception.CommunityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    /**
     * Method to handle all exceptions
     * @param ex
     * @return ResponseEntity<ErrorMessage>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> generateErrorMessage(Exception ex){

        ErrorMessage error = null;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        log.error("Exception captured: " + ex.getLocalizedMessage());

        if(ex instanceof HttpMessageNotReadableException || ex instanceof CommunityException){
            error = ErrorMessage.builder().httpCode(HttpStatus.BAD_REQUEST).message(ex.getMessage()).time(LocalDateTime.now()).build();
        }else{
            error = ErrorMessage.builder().httpCode(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage()).time(LocalDateTime.now()).build();
        }

        return new ResponseEntity<>(error, httpStatus);
    }
}
