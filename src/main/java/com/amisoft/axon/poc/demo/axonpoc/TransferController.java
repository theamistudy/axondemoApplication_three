package com.amisoft.axon.poc.demo.axonpoc;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.CreateAccountCommand;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.RequestMoneyTransferCommand;
import com.amisoft.axon.poc.demo.axonpoc.query.AccountBalance;
import com.amisoft.axon.poc.demo.axonpoc.query.AccountBalanceEventHandler;
import com.amisoft.axon.poc.demo.axonpoc.query.TransactionHistory;
import com.amisoft.axon.poc.demo.axonpoc.query.TransactionHistoryEventHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class TransferController {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    AccountBalanceEventHandler accountBalanceEventHandler;

    @Autowired
    TransactionHistoryEventHandler transactionHistoryEventHandler;

    @RequestMapping("/transfer")
    @ResponseBody
    public String sendMessage(){

        commandGateway.send(new CreateAccountCommand("1234",1000,LoggingCallback.INSTANCE));
        commandGateway.send(new CreateAccountCommand("4321",1000, LoggingCallback.INSTANCE));
        commandGateway.send(new RequestMoneyTransferCommand("tf1","1234","4321",1000,LoggingCallback.INSTANCE));

        return "Money transfer successful";

    }

    @GetMapping(value = "/balance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountBalance getBalance(@PathVariable String id){
        AccountBalance balance =  accountBalanceEventHandler.getBalance(id);
        return balance;
    }


    @GetMapping(value = "/transaction/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionHistory> findTransactions(@PathVariable String accountId){

        List<TransactionHistory> transactionDetails = transactionHistoryEventHandler.findTransactions(accountId);
        return transactionDetails;

    }





}
