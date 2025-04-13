package com.example.practice.BankingApp.Service;

import com.example.practice.BankingApp.Dto.AccountDto;
import com.example.practice.BankingApp.Entity.Account;
import com.example.practice.BankingApp.ExceptionHandler.AccountNotFoundException;
import com.example.practice.BankingApp.Mapper.AccountMapper;
import com.example.practice.BankingApp.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public AccountDto createAccount(AccountDto accountdto) {
        Account account = AccountMapper.mapToAccount(accountdto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountDetailById(Long id) {
       Account account = accountRepository.findById(id)
               .orElseThrow(()-> new AccountNotFoundException("Account Not found"));
       return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto depositAmount(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFoundException("Account Not found"));
        double totalBalance = account.getBalance() + amount;
        account.setBalance(totalBalance);
        accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }
}
