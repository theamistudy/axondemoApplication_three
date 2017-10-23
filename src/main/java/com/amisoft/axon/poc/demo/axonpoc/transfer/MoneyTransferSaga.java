package com.amisoft.axon.poc.demo.axonpoc.transfer;


import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.*;
import org.axonframework.spring.stereotype.Saga;
import com.amisoft.axon.poc.demo.axonpoc.LoggingCallback;

import javax.inject.Inject;

import static org.axonframework.eventhandling.saga.SagaLifecycle.end;


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



    /*@StartSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferRequestedEvent event) {
        targetAccount = event.getTargetAccount();
        transferId = event.getTransferId();
        SagaLifecycle.associateWith("transactionId", transferId);
        commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), transferId, event.getAmount()),
                new CommandCallback<WithdrawMoneyCommand, Object>() {
                    @Override
                    public void onSuccess(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Object result) {

                    }

                    @Override
                    public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable cause) {
                        commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
                    }
                });
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event) {
        commandGateway.send(new DepositMoneyCommand(targetAccount, event.getTransactionId(), event.getAmount()),
                LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyDepositedEvent event) {
        commandGateway.send(new CompleteMoneyTransferCommand(transferId),
                LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCompletedEvent event) {
    }

    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCancelledEvent event) {
        end();
    }*/
}
