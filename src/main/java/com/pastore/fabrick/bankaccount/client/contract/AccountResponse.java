package com.pastore.fabrick.bankaccount.client.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AccountResponse {
    private String accountId;
    private String iban;
    private String abiCode;
    private String countryCode;
    private String internationalCin;
    private String account;
    private String alias;
    private String productName;
    private String holderName;
    private Instant activateDate;
    private String currency;
}