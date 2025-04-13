package com.example.practice.BankingApp.Service;

import com.example.practice.BankingApp.Dto.AccountDto;

public interface AccountService {

    AccountDto createAccount(AccountDto accountdto);

    AccountDto getAccountDetailById(Long id);

    AccountDto depositAmount(Long id, double amount);
}
