package com.example.practice.BankingApp.ExceptionHandler;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message){
        super(message);
    }
}
