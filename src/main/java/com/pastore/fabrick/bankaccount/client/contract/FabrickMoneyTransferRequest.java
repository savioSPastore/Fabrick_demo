package com.pastore.fabrick.bankaccount.client.contract;

import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FabrickMoneyTransferRequest {

    private Creditor creditor;
    private String executionDate;
    private String description;
    private Long amount;
    private String currency;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class Creditor {
        private String name;
        private Account account;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class Account {
        private String bicCode;
    }

    public static FabrickMoneyTransferRequest from(MoneyTransferRequest moneyTransferRequest) {
        return FabrickMoneyTransferRequest.builder()
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
    }
}
