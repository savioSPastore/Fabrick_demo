package com.pastore.fabrick.bankaccount.controller;

import com.pastore.fabrick.bankaccount.contract.GetAccountResponse;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import com.pastore.fabrick.bankaccount.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/fabrick/demo/api/v1/account")
    public GetAccountResponse getAccount() {
        return bankAccountService.getAccount("14537780");
    }

    @PostMapping("/fabrick/demo/api/v1/money-transfer")
    public void moneyTransfer(@RequestBody MoneyTransferRequest moneyTransferRequest) {
        bankAccountService.moneyTransfer(moneyTransferRequest);
    }

}
