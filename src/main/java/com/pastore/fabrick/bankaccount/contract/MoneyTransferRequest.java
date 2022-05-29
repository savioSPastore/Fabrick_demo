package com.pastore.fabrick.bankaccount.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MoneyTransferRequest {
    @NotBlank
    private String accountId;
    @NotBlank
    private String receiverName;
    @NotBlank
    private String description;
    @NotBlank
    private String currency;
    @NotBlank
    private String amount;
    @NotBlank
    private String executionDate;
}
