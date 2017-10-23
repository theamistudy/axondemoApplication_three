package com.amisoft.axon.poc.demo.axonpoc.transfer;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.*;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
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
        commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(),event.getTransferId(),event.getAmount()), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    public void on(MoneyWithdrawnEvent event){

         commandGateway.send(new DepositMoneyCommand(targetAccount,event.getTransactionId(),event.getAmount()),LoggingCallback.INSTANCE);
    }


    @SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    public void on (MoneyDepositedEvent event){

        commandGateway.send(new CompleteMoneyTransferCommand(event.getTransactionId()),LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCompletedEvent event){

    }


}
