package com.pastore.fabrick.bankaccount.service;

import com.pastore.fabrick.bankaccount.client.FabrickClient;
import com.pastore.fabrick.bankaccount.client.contract.FabrickAccountResponse;
import com.pastore.fabrick.bankaccount.client.contract.FabrickBalance;
import com.pastore.fabrick.bankaccount.client.contract.FabrickMoneyTransferRequest;
import com.pastore.fabrick.bankaccount.client.contract.FabrickResult;
import com.pastore.fabrick.bankaccount.client.contract.FabrickResultList;
import com.pastore.fabrick.bankaccount.client.contract.FabrickTransactions;
import com.pastore.fabrick.bankaccount.contract.GetAccountResponse;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import com.pastore.fabrick.bankaccount.entity.Transaction;
import com.pastore.fabrick.bankaccount.entity.TransactionRepository;
import com.pastore.fabrick.bankaccount.exception.MoneyTransferKoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BankAccountService {
    private final FabrickClient fabrickClient;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankAccountService(FabrickClient fabrickClient, TransactionRepository transactionRepository) {
        this.fabrickClient = fabrickClient;
        this.transactionRepository = transactionRepository;
    }

    public GetAccountResponse getAccount(String accountId) {
        var accounts = fabrickClient.getAccount();
        return GetAccountResponse.builder().account(accounts.getPayload().getList().stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElseGet(FabrickAccountResponse::new))
                .build();
    }

    public void moneyTransfer(MoneyTransferRequest moneyTransferRequest) {
        FabrickMoneyTransferRequest fabrickMoneyTransferRequest = FabrickMoneyTransferRequest.from(moneyTransferRequest);
        try {
            fabrickClient.moneyTransfer(fabrickMoneyTransferRequest, moneyTransferRequest.getAccountId());
        } catch (Exception e) {
            throw new MoneyTransferKoException();
        }
    }

    public FabrickResultList<FabrickTransactions> getTransactions(String accountId, String fromDate, String toDate) {
        var results = fabrickClient.getTransactions(fromDate, toDate, accountId);
        transactionRepository.saveAll(results.getPayload().getList().stream().map(transaction ->
                Transaction.builder()
                        .transactionId(transaction.getTransactionId())
                        .type(transaction.getType().getValue())
                        .amount(transaction.getAmount())
                        .currency(transaction.getCurrency())
                        .description(transaction.getDescription())
                        .valueDate(transaction.getValueDate())
                        .build())
                .collect(Collectors.toList()));
        return results;
    }

    public FabrickResult<FabrickBalance> getBalance(String accountId) {
        return fabrickClient.getBalance(accountId);
    }
}
