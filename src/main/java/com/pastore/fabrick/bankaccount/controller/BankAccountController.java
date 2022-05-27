package com.pastore.fabrick.bankaccount.controller;

import com.pastore.fabrick.bankaccount.service.BankAccountService;
import com.pastore.fabrick.bankaccount.contract.GetAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/fabrick/demo/account")
    public GetAccountResponse getAccount() {
        return bankAccountService.getAccount("14537780");
    }
}
