package com.turkcell.discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication // Bu anotasyon ile uygulama Eureka discovery server (servis registry) olarak başlar.
@EnableEurekaServer // Eureka server'ı aktifleştirir.
public class DiscoveryServerApplication {
    // Uygulamanın başlangıç metodu.
	public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args); // Spring Boot uygulamasını başlatır.
	}
}
