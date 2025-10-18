package com.turkcell.book_services.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    @GetMapping
    public String get(@RequestParam String name) {
        System.out.println("Bu konsoldaki book service çalıştı.");
        return "Merhaba" + name + " , Book-Service";
    }
}
