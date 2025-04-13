package com.example.practice.BankingApp.Dto;


import lombok.*;

@Data
public class AccountDto {

    private Long id;

    public AccountDto() {
    }

    private String accountHolderName;
    private double balance;

    public AccountDto(Long id, String accountHolderName, double balance) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    //Creating Getter and Setter Manually as Lombok is not setup properly in my machine
    public Long getId() {
        return id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
