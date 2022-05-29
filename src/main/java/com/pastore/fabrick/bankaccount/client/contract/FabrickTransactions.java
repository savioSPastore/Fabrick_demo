package com.pastore.fabrick.bankaccount.client.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FabrickTransactions {
    private String transactionId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private Type type;
    private Long amount;
    private String currency;
    private String description;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class Type {
        private String enumeration;
        private String value;
    }
}
