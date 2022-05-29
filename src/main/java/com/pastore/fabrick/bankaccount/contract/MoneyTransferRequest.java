package com.pastore.fabrick.bankaccount.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MoneyTransferRequest {
    private String accountId;
    private String receiverName;
    private String description;
    private String currency;
    private String amount;
    private String executionDate;
}
