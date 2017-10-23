package com.amisoft.axon.poc.demo.axonpoc;

import com.amisoft.axon.poc.demo.axonpoc.coreapi.CreateAccountCommand;
import com.amisoft.axon.poc.demo.axonpoc.coreapi.RequestMoneyTransferCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;
import org.axonframework.spring.config.EnableAxon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@EntityScan(basePackageClasses = DomainEventEntry.class)
@EnableAxon
@SpringBootApplication
public class AxonpocApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(AxonpocApplication.class, args);

		CommandGateway commandGateway = context.getBean(CommandGateway.class);

		commandGateway.send(new CreateAccountCommand("1234", 1000), LoggingCallback.INSTANCE);
		commandGateway.send(new CreateAccountCommand("4321", 1000), LoggingCallback.INSTANCE);
		commandGateway.send(new RequestMoneyTransferCommand("tf1", "1234", "4321", 100), LoggingCallback.INSTANCE);
	}

	@Bean
	public EventStorageEngine eventStorageEngine(){
		return new InMemoryEventStorageEngine();
	}
}
