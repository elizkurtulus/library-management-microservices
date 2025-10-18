package com.turkcell.loan_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanServicesApplication.class, args);
	}

}
