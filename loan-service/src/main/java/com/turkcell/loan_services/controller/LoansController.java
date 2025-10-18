package com.turkcell.loan_services.controller;

import com.turkcell.loan_services.client.BookClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/loans")
@RestController
public class LoansController {
    private BookClient bookClient;

    public LoansController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @GetMapping
    public String get(@RequestParam String name) {
        //synchro communication
        String responseFromBookClient = bookClient.get(name);
        return "Bu konsoldaki loan service çalıştı." + responseFromBookClient;
    }
}
