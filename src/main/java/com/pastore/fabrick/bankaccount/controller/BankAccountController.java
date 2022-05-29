package com.pastore.fabrick.bankaccount.controller;

import com.pastore.fabrick.bankaccount.client.contract.FabrickBalance;
import com.pastore.fabrick.bankaccount.client.contract.FabrickResult;
import com.pastore.fabrick.bankaccount.client.contract.FabrickResultList;
import com.pastore.fabrick.bankaccount.client.contract.FabrickTransactions;
import com.pastore.fabrick.bankaccount.contract.GetAccountResponse;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import com.pastore.fabrick.bankaccount.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/fabrick/demo/api/v1/account/{accountId}")
    public GetAccountResponse getAccount(@PathVariable String accountId) {
        return bankAccountService.getAccount("14537780");
    }

    @GetMapping("/fabrick/demo/api/v1/account/{accountId}/balance")
    public FabrickResult<FabrickBalance> getBalance(@PathVariable String accountId) {
        return bankAccountService.getBalance(accountId);
    }

    @PostMapping("/fabrick/demo/api/v1/money-transfer")
    public void moneyTransfer(@RequestBody @Valid MoneyTransferRequest moneyTransferRequest) {
        bankAccountService.moneyTransfer(moneyTransferRequest);
    }

    @GetMapping("/fabrick/demo/api/v1/account/{accountId}/transactions")
    public FabrickResultList<FabrickTransactions> getTransactions(@RequestParam String fromDate,
                                                                  @RequestParam String toDate,
                                                                  @PathVariable String accountId) {
       return bankAccountService.getTransactions(accountId, fromDate, toDate);
    }
}
