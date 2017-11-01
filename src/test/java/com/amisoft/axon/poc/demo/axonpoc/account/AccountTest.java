package com.amisoft.axon.poc.demo.axonpoc.account;

import com.amisoft.axon.poc.demo.axonpoc.LoggingCallback;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;


public class AccountTest {
    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(Account.class);
    }

    @Test
    public void testAccountCreated() throws Exception {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand("1234", 1000, LoggingCallback.INSTANCE))
                .expectEvents(new AccountCreatedEvent("1234", 1000));
    }

    @Test
    public void testWithdrawReasonableAmount() throws Exception {
        fixture.given(new AccountCreatedEvent("1234", 1000))
                .when(new WithdrawMoneyCommand("1234", "tx1", 100))
                .expectEvents(new MoneyWithdrawnEvent("1234", "tx1", 100, -100));
    }

    @Test
    public void testWithdrawUnreasonableAmount() throws Exception {
        fixture.given(new AccountCreatedEvent("1234", 1000))
                .when(new WithdrawMoneyCommand("1234", "tx1", 1010))
                .expectNoEvents();
    }

    @Test
    public void testDepositMoney() {
        fixture.given(new AccountCreatedEvent("1234", 1000),
                new MoneyDepositedEvent("1234", "tx1", 250, 250))
                .when(new DepositMoneyCommand("1234", "tx1", 500))
                .expectEvents(new MoneyDepositedEvent("1234", "tx1", 500, 750));
    }

}