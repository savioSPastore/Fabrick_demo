package com.pastore.fabrick.bankaccount.contract;

import com.pastore.fabrick.bankaccount.client.contract.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GetAccountResponse {
    private AccountResponse account;
}
