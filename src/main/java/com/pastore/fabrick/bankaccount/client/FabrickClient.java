package com.pastore.fabrick.bankaccount.client;

import com.pastore.fabrick.bankaccount.client.contract.FabrickAccountResponse;
import com.pastore.fabrick.bankaccount.client.contract.FabrickBalance;
import com.pastore.fabrick.bankaccount.client.contract.FabrickMoneyTransferRequest;
import com.pastore.fabrick.bankaccount.client.contract.FabrickResult;
import com.pastore.fabrick.bankaccount.client.contract.FabrickResultList;
import com.pastore.fabrick.bankaccount.client.contract.FabrickTransactions;
import com.pastore.fabrick.bankaccount.exception.MoneyTransferKoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FabrickClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;
    private final String authSchema;

    @Autowired
    public FabrickClient(RestTemplate restTemplate,
                         @Value("${com.pastore.fabrick.baseurl}") String baseUrl,
                         @Value("${com.pastore.fabrick.apikey}") String apiKey,
                         @Value("${com.pastore.fabrick.authschema}") String authSchema) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.authSchema = authSchema;
    }

    public FabrickResultList<FabrickAccountResponse> getAccount() {
        return restTemplate.exchange(baseUrl + "/api/gbs/banking/v4.0/accounts",
                HttpMethod.GET, new HttpEntity<>(getHeader()),
                new ParameterizedTypeReference<FabrickResultList<FabrickAccountResponse>>() {
                }).getBody();
    }

    public void moneyTransfer(FabrickMoneyTransferRequest fabrickMoneyTransferRequest, String accountId) {
        var header = getHeader();
        header.set("X-Time-Zone", "Europe/Rome");
        try {
            restTemplate.exchange(baseUrl + String.format("/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers",
                    accountId),
                    HttpMethod.POST, new HttpEntity<>(fabrickMoneyTransferRequest, header),
                    Void.class);
        } catch (Exception e) {
            throw new MoneyTransferKoException();
        }
    }

    public FabrickResultList<FabrickTransactions> getTransactions(String fromDate, String toDate, String accountId) {
        return restTemplate.exchange(baseUrl +
                        String.format("/api/gbs/banking/v4.0/accounts/%s/transactions?fromAccountingDate=%s&toAccountingDate=%s"
                                , accountId, fromDate, toDate),
                HttpMethod.GET, new HttpEntity<>(getHeader()),
                new ParameterizedTypeReference<FabrickResultList<FabrickTransactions>>() {
                }).getBody();
    }

    public FabrickResult<FabrickBalance> getBalance(String accountId) {
        return restTemplate.exchange(baseUrl +
                        String.format("/api/gbs/banking/v4.0/accounts/%s/balance", accountId),
                HttpMethod.GET, new HttpEntity<>(getHeader()),
                new ParameterizedTypeReference<FabrickResult<FabrickBalance>>() {
                }).getBody();
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);
        headers.set("Auth-Schema", authSchema);
        return headers;
    }
}
