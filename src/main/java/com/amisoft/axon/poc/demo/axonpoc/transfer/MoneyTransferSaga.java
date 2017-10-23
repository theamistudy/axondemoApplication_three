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

        // Oneway of handle overdraft limit exceed exception. Just uncomment the below code.
        /*try {
            commandGateway.sendAndWait(new WithdrawMoneyCommand(event.getSourceAccount(), event.getTransferId(), event.getAmount()));
        } catch(CommandExecutionException e){

            if(OverdraftLimitExceededException.class.isInstance(e.getCause())){

                commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
            }*/

             commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), event.getTransferId(), event.getAmount()), new CommandCallback<WithdrawMoneyCommand, Object>() {
                 @Override
                 public void onSuccess(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Object o) {

                 }

                 @Override
                 public void onFailure(CommandMessage<? extends WithdrawMoneyCommand> commandMessage, Throwable throwable) {

                     commandGateway.send(new CancelMoneyTransferCommand(event.getTransferId()));
                 }
             });


        }


    @SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    public void on(MoneyWithdrawnEvent event){

         commandGateway.send(new DepositMoneyCommand(targetAccount,event.getTransactionId(),event.getAmount()),LoggingCallback.INSTANCE);
    }


    @SagaEventHandler(associationProperty = "transactionId", keyName="transferId")
    public void on (MoneyDepositedEvent event){

        commandGateway.send(new CompleteMoneyTransferCommand(event.getTransactionId()),LoggingCallback.INSTANCE);
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
