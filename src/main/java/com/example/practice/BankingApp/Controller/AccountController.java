package com.example.practice.BankingApp.Controller;


import com.example.practice.BankingApp.Dto.AccountDto;
import com.example.practice.BankingApp.Service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    private AccountServiceImpl accountService;


    //Create Account
    @PostMapping("/createAccount")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }


    //Get the Account Detail By Id
    @GetMapping("/getAccountDetail/{id}")
    public ResponseEntity<AccountDto> getAccountDetailById(@PathVariable Long id){
        AccountDto account = accountService.getAccountDetailById(id);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<AccountDto> depositAmount(@PathVariable Long id, @RequestBody Map<String,Double> request){
        double amount = request.get("amount");
        AccountDto account = accountService.depositAmount(id,amount);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @GetMapping("/allAccount")
    public ResponseEntity<List<AccountDto>> getAllAccountDetails(){
        List<AccountDto> accountDetails = accountService.getAllAccountDetails();
        return new ResponseEntity<>(accountDetails,HttpStatus.OK);
    }


    @PutMapping("/withdraw/{id}")
    public ResponseEntity<AccountDto> withdrawAmount(@PathVariable Long id, @RequestBody Map<String , Double> request){
        double amount = request.get("amount");
        AccountDto account = accountService.withdrawAmount(id,amount);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @PutMapping("/changeName/{id}")
    public ResponseEntity<AccountDto> changeAccountHolderName(@PathVariable Long id,
                                                              @RequestBody Map<String, String> request){
        String accountHolderName = request.get("name");
        AccountDto accountDetails = accountService.editAccountHolderNamed(id,accountHolderName);
        return new ResponseEntity<>(accountDetails,HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Account Deleted Successfully",HttpStatus.OK);
    }


}
