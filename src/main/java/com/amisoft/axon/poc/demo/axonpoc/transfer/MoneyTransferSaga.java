package com.amisoft.axon.poc.demo.axonpoc.transfer;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.*;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.inject.Inject;
import java.util.UUID;


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
        transferId = event.getTransferId();
        SagaLifecycle.associateWith("transactionId",transferId);

         commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), transferId, event.getAmount()), new CommandCallback<WithdrawMoneyCommand, Object>() {
             @Override
             public void onSuccess(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Object o) {

             }

             @Override
             public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable throwable) {

                 commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
             }
         });


        }


   // @SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(MoneyWithdrawnEvent event){

         commandGateway.send(new DepositMoneyCommand(targetAccount,event.getTransactionId(),event.getAmount()),LoggingCallback.INSTANCE);
    }


    //@SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    @SagaEventHandler(associationProperty = "transactionId")
    public void on (MoneyDepositedEvent event){

        commandGateway.send(new CompleteMoneyTransferCommand(transferId),LoggingCallback.INSTANCE);
    }



    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCancelledEvent event){

        //One way of end saga. SagaLifeCycle static import
        end();
    }


    //Anotherway of end saga with annotation.
    @EndSaga
    @SagaEventHandler(associationProperty = "transferId")
    public void on(MoneyTransferCompletedEvent event){

    }


}
