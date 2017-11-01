package com.amisoft.axon.poc.demo.axonpoc.coreapi

import com.amisoft.axon.poc.demo.axonpoc.LoggingCallback
import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreateAccountCommand(@TargetAggregateIdentifier val accountId: String, val overdraftLimit: Int, val instance: LoggingCallback<Any, Any>);
class WithdrawMoneyCommand (@TargetAggregateIdentifier val accountId : String, val transactionId : String, val amount : Int);
class DepositMoneyCommand(@TargetAggregateIdentifier val accountId: String,  val transactionId : String, val amount: Int)



class AccountCreatedEvent( val accountId : String, val overdraftLimit : Int);

//balance Update


abstract class BalanceUpdatedEvent(val accountId: String, val balance: Int)

class MoneyWithdrawnEvent(accountId: String, val transactionId : String, val amount: Int, balance: Int) : BalanceUpdatedEvent(accountId, balance)
class MoneyDepositedEvent(accountId: String, val transactionId : String, val amount: Int, balance: Int) : BalanceUpdatedEvent(accountId, balance)

class OverdraftLimitExceededException : Exception()