package com.turkcell.fine_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FineServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FineServicesApplication.class, args);
	}

}
