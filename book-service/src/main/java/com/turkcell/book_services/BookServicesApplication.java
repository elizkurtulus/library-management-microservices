package com.turkcell.book_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Feign client'ları etkinleştir (category service ile iletişim için)
@EnableFeignClients
@SpringBootApplication
public class BookServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServicesApplication.class, args);
	}

}
