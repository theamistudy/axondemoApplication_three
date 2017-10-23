package com.amisoft.axon.poc.demo.axonpoc.account;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.AccountCreatedEvent;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.CreateAccountCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.*;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


@Aggregate
@NoArgsConstructor
public class Account {

    @AggregateIdentifier
    private String accountId;
    private int balance;
    private int overdraftLimit;

    @CommandHandler
    public Account(CreateAccountCommand cmd) {
        apply(new AccountCreatedEvent(cmd.getAccountId(), cmd.getOverdraftLimit()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.overdraftLimit = event.getOverdraftLimit();
    }



    @CommandHandler
    public void handle(WithdrawMoneyCommand cmd) throws OverdraftLimitExceededException {
        if (balance + overdraftLimit < cmd.getAmount()) {
            throw new OverdraftLimitExceededException();
        }
        apply(new MoneyWithdrawnEvent(accountId, cmd.getAmount(), balance - cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(MoneyWithdrawnEvent event){

        this.balance = event.getBalance();

    }

}
