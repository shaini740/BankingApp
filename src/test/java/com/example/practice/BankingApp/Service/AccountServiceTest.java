package com.example.practice.BankingApp.Service;


import com.example.practice.BankingApp.Dto.AccountDto;
import com.example.practice.BankingApp.Entity.Account;
import com.example.practice.BankingApp.ExceptionHandler.AccountNotFoundException;
import com.example.practice.BankingApp.ExceptionHandler.InsufficientBalanceException;
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

import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        verify(accountRepository,atLeastOnce()).save(any(Account.class));
    }

    @DisplayName("Test Get Details By Id")
    @Test
    public void testGetDetailsById(){
       when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
       AccountDto response = accountService.getAccountDetailById(account.getId());
        Assertions.assertEquals(accountdto.getAccountHolderName(),response.getAccountHolderName());
        Assertions.assertEquals(accountdto.getId(),response.getId());
        Assertions.assertEquals(accountdto.getBalance(),response.getBalance());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }

    @DisplayName("Test Get All The details By Id Exception")
    @Test
    public void TestGetDetailsByIDException(){
        when(accountRepository.findById(account.getId())).thenThrow(new AccountNotFoundException("Account Not found"));
        Exception exception  = Assertions.assertThrows(AccountNotFoundException.class, ()->{
            accountService.getAccountDetailById(account.getId());
        });
        Assertions.assertEquals("Account Not found",exception.getMessage());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }


    @DisplayName("Test Deposit Amount")
    @Test
    public void TestDepositAmount(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto response = accountService.depositAmount(1L,5000);
        Assertions.assertEquals(response.getBalance(),accountdto.getBalance()+5000);
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
        verify(accountRepository,atLeastOnce()).save(any(Account.class));
    }

    @DisplayName("Test Deposit Amount throws Exception")
    @Test
    public void TestDepositAmountThrowsException(){
        when(accountRepository.findById(account.getId())).thenThrow(new AccountNotFoundException("Account Not found"));
        Exception exception  = Assertions.assertThrows(AccountNotFoundException.class, ()->{
            accountService.depositAmount(1L,5000);
        });
        Assertions.assertEquals("Account Not found",exception.getMessage());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }

    @DisplayName("Test edit Account Holder Name")
    @Test
    public void testEditAccountHolderName(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
        accountdto.setAccountHolderName("Fooby");
        AccountMapper.mapToAccountDto(account);
        AccountDto response = accountService.editAccountHolderNamed(1L,"Fooby");
        Assertions.assertEquals(response.getAccountHolderName(),accountdto.getAccountHolderName());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
        verify(accountRepository,atLeastOnce()).save(any(Account.class));
    }

    @DisplayName("Test Edit Account Holder Name throws Exception")
    @Test
    public void testEditAccountHolderNameThrowsException(){
        when(accountRepository.findById(account.getId())).thenThrow(new AccountNotFoundException("Account Details Not found"));
        Exception exception = Assertions.assertThrows(AccountNotFoundException.class, ()->{
            accountService.editAccountHolderNamed(1L,"Fooby");
        });
        Assertions.assertEquals("Account Details Not found",exception.getMessage());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }

    @DisplayName("Test delete Account")
    @Test
    public void testDeleteAccount(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
        accountService.deleteAccount(1L);
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
        verify(accountRepository,atLeastOnce()).deleteById(any(Long.class));
    }

    @DisplayName("Test delete Account Throws Exception")
    @Test
    public void testDeleteAccountThrowsException(){
        when(accountRepository.findById(account.getId())).thenThrow(new AccountNotFoundException("Account Details Not found"));
        Exception exception = Assertions.assertThrows(AccountNotFoundException.class,()->{
            accountService.deleteAccount(1L);
        });
        Assertions.assertEquals("Account Details Not found",exception.getMessage());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }

    @DisplayName("Test Withdraw Amount")
    @Test
    public void testWithdrawAmount(){
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
        AccountDto response = accountService.withdrawAmount(1L,5000);
        Assertions.assertEquals(response.getBalance(),accountdto.getBalance()-5000);
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
        verify(accountRepository,atLeastOnce()).save(any(Account.class));
    }

    @DisplayName("Test Withdraw Amount throws AccountFoundException")
    @Test
    public void  testWithdrawAmountThrowsAccountNotFoundException(){
        when(accountRepository.findById(account.getId())).thenThrow(new
                AccountNotFoundException("Account Details Not found"));
        Exception exception = Assertions.assertThrows(AccountNotFoundException.class,()->{
            accountService.withdrawAmount(1L,5000);
        });
        Assertions.assertEquals("Account Details Not found",exception.getMessage());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }

    @DisplayName("Test Withdraw Amount Throws Insufficient Balance Exception")
    @Test
    public void testWithdrawAmountThrowsInsufficientBalanceException(){
        when(accountRepository.findById(account.getId())).thenThrow(new InsufficientBalanceException("Insuficient Balance" +
                " :  The Balance is less than Amount entered"));
        Exception exception = Assertions.assertThrows(InsufficientBalanceException.class,()->{
            accountService.withdrawAmount(1L,5500);
        });
        Assertions.assertEquals("Insuficient Balance :  The Balance is less than Amount entered",
                exception.getMessage());
        verify(accountRepository,atLeastOnce()).findById(any(Long.class));
    }

    @DisplayName("Test All the Account details")
    @Test
    public void testGetAllAccountDetails(){
        when(accountRepository.findAll()).thenReturn(java.util.List.of(account));
        List<AccountDto> response = accountService.getAllAccountDetails();
        Assertions.assertEquals(accountdto.getAccountHolderName(),response.get(0).getAccountHolderName());
        verify(accountRepository,atLeastOnce()).findAll();
    }
}
