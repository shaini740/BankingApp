package com.example.practice.BankingApp.ExceptionHandler;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message){
        super(message);
    }
}
