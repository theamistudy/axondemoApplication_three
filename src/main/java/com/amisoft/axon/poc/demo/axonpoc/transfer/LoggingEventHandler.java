package com.amisoft.axon.poc.demo.axonpoc.transfer;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;


@Component
public class LoggingEventHandler {

    @EventHandler
    public void on(Object event) {
        System.out.println("Event received: " + event.toString());
    }
}
