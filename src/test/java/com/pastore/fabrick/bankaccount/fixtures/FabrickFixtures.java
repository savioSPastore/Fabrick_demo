package com.pastore.fabrick.bankaccount.fixtures;

import com.pastore.fabrick.bankaccount.client.contract.FabrickResultList;
import com.pastore.fabrick.bankaccount.client.contract.FabrickTransactions;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;

import java.util.List;

public class FabrickFixtures {

    public static FabrickResultList<FabrickTransactions> getValidTransactions() {
        return FabrickResultList.<FabrickTransactions>builder()
                .payload(FabrickResultList.Payload.<FabrickTransactions>builder()
                        .list(List.of(
                                FabrickTransactions.builder()
                                        .type(FabrickTransactions.Type.builder()
                                                .enumeration("TYPE")
                                                .value("TYPE")
                                                .build())
                                        .valueDate("2019-01-02")
                                        .amount(300L)
                                        .currency("EUR")
                                        .transactionId("A0001")
                                        .accountingDate("2019-01-02")
                                        .build(),
                                FabrickTransactions.builder()
                                        .type(FabrickTransactions.Type.builder()
                                                .enumeration("TYPE")
                                                .value("TYPE")
                                                .build())
                                        .valueDate("2019-02-06")
                                        .amount(300L)
                                        .currency("EUR")
                                        .transactionId("A0002")
                                        .accountingDate("2019-02-07")
                                        .build()
                        ))
                        .build())
                .build();
    }

    public static MoneyTransferRequest getValidMoneyTransferRequest() {
        return MoneyTransferRequest.builder()
                .accountId("00001")
                .description("This is a description")
                .executionDate("2020-01-01")
                .currency("EUR")
                .receiverName("Mario Rossi")
                .amount("300")
                .build();
    }
}
