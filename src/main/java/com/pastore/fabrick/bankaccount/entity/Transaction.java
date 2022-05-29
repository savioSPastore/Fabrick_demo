package com.pastore.fabrick.bankaccount.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@Entity(name = "transactions")
public class Transaction {
    @Id
    private String transactionId;
    @Column
    private String operationId;
    @Column
    private String accountingDate;
    @Column
    private String valueDate;
    @Column
    private String type;
    @Column
    private Long amount;
    @Column
    private String currency;
    @Column
    private String description;
}
