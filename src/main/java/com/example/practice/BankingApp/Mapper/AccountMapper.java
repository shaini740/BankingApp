package com.example.practice.BankingApp.Mapper;

import com.example.practice.BankingApp.Dto.AccountDto;
import com.example.practice.BankingApp.Entity.Account;
import org.jetbrains.annotations.NotNull;

public class AccountMapper {


    public static  Account mapToAccount(AccountDto accountdto){
        Account account = new Account(
                accountdto.getId(),
                accountdto.getAccountHolderName(),
                accountdto.getBalance()
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }
}
