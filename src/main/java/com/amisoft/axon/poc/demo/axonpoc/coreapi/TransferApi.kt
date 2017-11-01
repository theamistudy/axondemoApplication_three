package com.amisoft.axon.poc.demo.axonpoc.coreapi

import com.amisoft.axon.poc.demo.axonpoc.LoggingCallback
import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.axonframework.commandhandling.model.AggregateIdentifier




class RequestMoneyTransferCommand(@AggregateIdentifier val transferId : String, val sourceAccount : String, val targetAccount : String, val amount : Int,val instance: LoggingCallback<Any, Any>);

class MoneyTransferRequestedEvent( val transferId : String,val sourceAccount : String, val targetAccount : String, val amount : Int);



class CompleteMoneyTransferCommand (@TargetAggregateIdentifier val transferId : String);
class MoneyTransferCompletedEvent( val transferId : String);



class CancelMoneyTransferCommand (@TargetAggregateIdentifier val transferId : String);
class MoneyTransferCancelledEvent( val transferId : String);