package com.amisoft.axon.poc.demo.axonpoc.query;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TransactionHistory {


    @Id
    @GeneratedValue
    private Long id;

    private String accountId;
    private int amount;


    public TransactionHistory() {
    }

    public TransactionHistory(String accountId, int amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public TransactionHistory(String accountId) {

        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
