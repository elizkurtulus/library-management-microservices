package com.turkcell.loan_services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service", path = "/api/v1/books")
public interface BookClient {
    @GetMapping
    String get(@RequestParam String name);
}
