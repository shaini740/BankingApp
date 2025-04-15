package com.example.practice.BankingApp.Service;


import com.example.practice.BankingApp.Dto.AccountDto;
import com.example.practice.BankingApp.Entity.Account;
import com.example.practice.BankingApp.ExceptionHandler.AccountNotFoundException;
import com.example.practice.BankingApp.Mapper.AccountMapper;
import com.example.practice.BankingApp.Repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {


    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    Account account = new Account();
    AccountDto accountdto = new AccountDto();

    @BeforeEach
    public void setup(){
       account.setAccountHolderName("Shaini");
       account.setBalance(234567);
       account.setId(1L);
       accountdto = AccountMapper.mapToAccountDto(account);
    }

    @DisplayName("Test Create Account")
    @Test
    public void testCreateAccount(){
        try (MockedStatic<AccountMapper> mocked = Mockito.mockStatic(AccountMapper.class)) {
            mocked.when(() -> AccountMapper.mapToAccount(accountdto)).thenReturn(account);
        }
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto response = accountService.createAccount(accountdto);
        Assertions.assertEquals(accountdto.getAccountHolderName(),response.getAccountHolderName());
        Assertions.assertEquals(accountdto.getId(),response.getId());
        Assertions.assertEquals(accountdto.getBalance(),response.getBalance());
    }

    @DisplayName("Test Get Details By Id")
    @Test
    public void testGetDetailsById(){
       when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
       AccountDto response = accountService.getAccountDetailById(account.getId());
        Assertions.assertEquals(accountdto.getAccountHolderName(),response.getAccountHolderName());
        Assertions.assertEquals(accountdto.getId(),response.getId());
        Assertions.assertEquals(accountdto.getBalance(),response.getBalance());
    }

    @DisplayName("Test Get All The details By Id Exception")
    @Test
    public void TestGetDetailsByIDException(){
        when(accountRepository.findById(account.getId())).thenThrow(new AccountNotFoundException("Account Not found"));
        Exception exception  = Assertions.assertThrows(AccountNotFoundException.class, ()->{
            accountService.getAccountDetailById(account.getId());
        });
        Assertions.assertEquals("Account Not found",exception.getMessage());
    }


    @DisplayName("Test Deposit Amount")
    @Test
    public void TestDepositAmount(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto response = accountService.depositAmount(1L,5000);
        Assertions.assertEquals(response.getBalance(),accountdto.getBalance()+5000);
    }

    @DisplayName("Test Deposit Amount throws Exception")
    @Test
    public void TestDepositAmountThrowsException(){
        when(accountRepository.findById(account.getId())).thenThrow(new AccountNotFoundException("Account Not found"));
        Exception exception  = Assertions.assertThrows(AccountNotFoundException.class, ()->{
            accountService.depositAmount(1L,5000);
        });
        Assertions.assertEquals("Account Not found",exception.getMessage());
    }



    //Need to write the test cases 

}
