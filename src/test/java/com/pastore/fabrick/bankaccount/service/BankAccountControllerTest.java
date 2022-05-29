package com.pastore.fabrick.bankaccount.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastore.fabrick.bankaccount.contract.MoneyTransferRequest;
import com.pastore.fabrick.bankaccount.controller.BankAccountController;
import com.pastore.fabrick.bankaccount.exception.MoneyTransferKoException;
import com.pastore.fabrick.bankaccount.fixtures.FabrickFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @MockBean
    private BankAccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void moneyTransfer_shouldThrowBadRequest() throws Exception {
        var request = FabrickFixtures.getValidMoneyTransferRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/fabrick/demo/api/v1/money-transfer")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("getInvalidMoneyTransferRequest")
    void moneyTransfer_shouldThrowBadRequest(MoneyTransferRequest request) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/fabrick/demo/api/v1/money-transfer")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void moneyTransfer_shouldThrowConflict() throws Exception {
        var request = FabrickFixtures.getValidMoneyTransferRequest();
        doThrow(MoneyTransferKoException.class).when(accountService).moneyTransfer(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/fabrick/demo/api/v1/money-transfer")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    private static Stream<MoneyTransferRequest> getInvalidMoneyTransferRequest() {
        return Stream.of(FabrickFixtures.getValidMoneyTransferRequest().toBuilder()
                        .amount(null)
                        .build(),
                FabrickFixtures.getValidMoneyTransferRequest().toBuilder()
                        .executionDate(null)
                        .build(),
                FabrickFixtures.getValidMoneyTransferRequest().toBuilder()
                        .receiverName(null)
                        .build(),
                FabrickFixtures.getValidMoneyTransferRequest().toBuilder()
                        .description(null)
                        .build(),
                FabrickFixtures.getValidMoneyTransferRequest().toBuilder()
                        .currency(null)
                        .build());
    }
}
