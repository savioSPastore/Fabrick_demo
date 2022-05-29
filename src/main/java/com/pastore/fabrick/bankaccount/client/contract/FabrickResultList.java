package com.pastore.fabrick.bankaccount.client.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FabrickResultList<T> {

    private String status;
    private List<String> error;
    private Payload<T> payload;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Payload<T> {
        private List<T> list;
    }
}
