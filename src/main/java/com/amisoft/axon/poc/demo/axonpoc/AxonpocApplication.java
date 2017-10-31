package com.amisoft.axon.poc.demo.axonpoc;

import org.axonframework.config.EventHandlingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class AxonpocApplication {

	public static void main(String[] args) {

		SpringApplication.run(AxonpocApplication.class,args);
	}


	@Autowired
	public void configure(EventHandlingConfiguration config){

		config.registerTrackingProcessor("TransactionHistory");

	}


}
