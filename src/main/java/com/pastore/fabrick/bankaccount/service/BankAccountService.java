package com.pastore.fabrick.bankaccount.service;

import com.pastore.fabrick.bankaccount.client.FabrickClient;
import com.pastore.fabrick.bankaccount.client.contract.AccountResponse;
import com.pastore.fabrick.bankaccount.client.contract.FabrickMoneyTransferRequest;
import com.pastore.fabrick.bankaccount.contract.GetAccountResponse;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
    private final FabrickClient fabrickClient;

    @Autowired
    public BankAccountService(FabrickClient fabrickClient) {
        this.fabrickClient = fabrickClient;
    }

    public GetAccountResponse getAccount(String accountId) {
        var accounts = fabrickClient.getAccount();
        return GetAccountResponse.builder().account(accounts.getPayload().getList().stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElseGet(AccountResponse::new))
                .build();
    }

    public void moneyTransfer(MoneyTransferRequest moneyTransferRequest) {
        FabrickMoneyTransferRequest fabrickMoneyTransferRequest = FabrickMoneyTransferRequest.builder()
                .executionDate(moneyTransferRequest.getExecutionDate())
                .currency(moneyTransferRequest.getCurrency())
                .amount(Long.getLong(moneyTransferRequest.getAmount()))
                .creditor(FabrickMoneyTransferRequest.Creditor
                        .builder()
                        .name(moneyTransferRequest.getReceiverName())
                        .account(FabrickMoneyTransferRequest.Account.builder()
                                .bicCode("SELBIT2BXXX")
                                .build())
                        .build())
                .build();
        fabrickClient.moneyTransfer(fabrickMoneyTransferRequest, moneyTransferRequest.getAccountId());
    }
}
