package com.example.practice.BankingApp.Service;

import com.example.practice.BankingApp.Dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountdto);

    AccountDto getAccountDetailById(Long id);

    AccountDto depositAmount(Long id, double amount);

    List<AccountDto> getAllAccountDetails();

    AccountDto withdrawAmount(Long id, double amount);

    AccountDto editAccountHolderNamed(long id, String accountHolderName);

    void deleteAccount (long id);
}
