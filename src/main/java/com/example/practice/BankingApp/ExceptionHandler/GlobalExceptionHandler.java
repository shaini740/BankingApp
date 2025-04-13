package com.example.practice.BankingApp.ExceptionHandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object>handlerForAccountNotFound(AccountNotFoundException ex){
        Map<String,Object> error = new HashMap<>();
        error.put("TimeStamp : ", LocalDate.now());
        error.put("Message : ",ex.getMessage());
        error.put("Status : ", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
