package com.amisoft.axon.poc.demo.axonpoc.query;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.MoneyDepositedEvent;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.MoneyWithdrawnEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;


@ProcessingGroup("TransactionHistory")
@Component
public class TransactionHistoryEventHandler {

    private final EntityManager entityManager;

    public TransactionHistoryEventHandler(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EventHandler
    public void on(MoneyDepositedEvent event){

        entityManager.persist(new TransactionHistory(event.getAccountId(),event.getTransactionId(),event.getAmount()));

    }


    @EventHandler
    public void on(MoneyWithdrawnEvent event){

        entityManager.persist(new TransactionHistory(event.getAccountId() ,event.getTransactionId(), - event.getAmount()));

    }


    public List<TransactionHistory> findTransactions(String accountId){

        List <TransactionHistory> history = entityManager.createQuery("select th from TransactionHistory th where th.accountId = :accountId order by th.id")
                .setParameter("accountId", accountId)
                .getResultList();

        return history;
    }



}
