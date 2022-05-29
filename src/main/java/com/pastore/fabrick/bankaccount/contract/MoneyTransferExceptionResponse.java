package com.pastore.fabrick.bankaccount.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MoneyTransferExceptionResponse {
    private String code;
    private String description;
}
