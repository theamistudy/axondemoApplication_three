package com.amisoft.axon.poc.demo.axonpoc.query;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.BalanceUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;


@Component
public class AccountBalanceEventHandler {


    private final AccountBalanceRepository repository;

    public AccountBalanceEventHandler(AccountBalanceRepository repository) {
        this.repository = repository;
    }


    @EventHandler
    public void on(BalanceUpdatedEvent event){

        repository.save(new AccountBalance(event.getAccountId(),event.getBalance()));

    }



    public AccountBalance getBalance(String id){
        AccountBalance balance =  repository.findOne(id);
        return balance;
    }

}
