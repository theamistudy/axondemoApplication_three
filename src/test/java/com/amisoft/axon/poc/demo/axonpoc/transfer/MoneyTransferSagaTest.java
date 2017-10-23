package com.amisoft.axon.poc.demo.axonpoc.transfer;


import com.amisoft.axon.poc.demo.axonpoc.coreapi.*;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;


public class MoneyTransferSagaTest {

    private SagaTestFixture fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new SagaTestFixture<>(MoneyTransferSaga.class);
    }

    @Test
    public void testMoneyTransferRequest() throws Exception {
        fixture.givenNoPriorActivity()
                .whenPublishingA(new MoneyTransferRequestedEvent("tf1", "acct1", "acct2", 100))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new WithdrawMoneyCommand("acct1", 100));
    }


}
