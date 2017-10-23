package com.amisoft.axon.poc.demo.axonpoc.transfer;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.MoneyTransferRequestedEvent;
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

        commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(),event.getAmount()));

    }


}
