package com.amisoft.axon.poc.demo.axonpoc.transfer;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.DepositMoneyCommand;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.MoneyTransferRequestedEvent;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.MoneyWithdrawnEvent;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.WithdrawMoneyCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.inject.Inject;


@Saga
public class MoneyTransferSaga {


    @Inject
    private transient CommandGateway commandGateway;

    private String targetAccount;
    private String transferId;


    @StartSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferRequestedEvent event){

        targetAccount = event.getTargetAccount();
        commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(),event.getTransferId(),event.getAmount()));
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    public void on(MoneyWithdrawnEvent event){

         commandGateway.send(new DepositMoneyCommand(targetAccount,event.getTransactionId(),event.getAmount()));

    }


}
