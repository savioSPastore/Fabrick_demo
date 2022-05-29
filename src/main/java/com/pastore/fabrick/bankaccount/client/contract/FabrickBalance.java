package com.pastore.fabrick.bankaccount.client.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FabrickBalance {
    private String date;
    private Long balance;
    private Long availableBalance;
    private String currency;
}
