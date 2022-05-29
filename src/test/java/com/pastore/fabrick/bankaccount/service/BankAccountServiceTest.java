package com.pastore.fabrick.bankaccount.service;

import com.pastore.fabrick.bankaccount.client.FabrickClient;
import com.pastore.fabrick.bankaccount.client.contract.FabrickMoneyTransferRequest;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import com.pastore.fabrick.bankaccount.entity.Transaction;
import com.pastore.fabrick.bankaccount.entity.TransactionRepository;
import com.pastore.fabrick.bankaccount.exception.MoneyTransferKoException;
import com.pastore.fabrick.bankaccount.fixtures.FabrickFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {
    @Mock
    FabrickClient fabrickClient;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    BankAccountService bankAccountService;

    @Test
    void getTransaction_shouldWork() {
        String from = "2019-01-01";
        String to = "2022-01-01";
        String accountId = "000001";
        var expected = FabrickFixtures.getValidTransactions();
        when(fabrickClient.getTransactions(from, to, accountId))
                .thenReturn(expected);
        var transactionsToSave = expected.getPayload().getList().stream().map(transaction ->
                Transaction.builder()
                        .transactionId(transaction.getTransactionId())
                        .type(transaction.getType().getValue())
                        .amount(transaction.getAmount())
                        .currency(transaction.getCurrency())
                        .description(transaction.getDescription())
                        .valueDate(transaction.getValueDate())
                        .build()).collect(Collectors.toList());
        var actual = bankAccountService.getTransactions(accountId, from, to);
        verify(transactionRepository).saveAll(transactionsToSave);
        assertEquals(expected, actual);
    }

    @Test
    void moneyTransfer_shouldWork() {
        var request = FabrickFixtures.getValidMoneyTransferRequest();
        var fabrickRequest = FabrickMoneyTransferRequest.from(request);

        bankAccountService.moneyTransfer(request);

        verify(fabrickClient).moneyTransfer(fabrickRequest, request.getAccountId());
    }

    @Test
    void moneyTransfer_shouldThrowMoneyTransferKoException() {
        MoneyTransferRequest moneyTransferRequest = FabrickFixtures.getValidMoneyTransferRequest();
        doThrow(RestClientException.class).when(fabrickClient).moneyTransfer(FabrickMoneyTransferRequest.from(moneyTransferRequest),
                moneyTransferRequest.getAccountId());
        assertThrows(MoneyTransferKoException.class, () -> bankAccountService.moneyTransfer(moneyTransferRequest));
    }
}
