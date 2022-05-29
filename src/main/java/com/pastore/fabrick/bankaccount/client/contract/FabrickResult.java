package com.pastore.fabrick.bankaccount.client.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FabrickResult<T> {
    private String status;
    private List<String> error;
    private T payload;
}
