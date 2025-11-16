package com.turkcell.book_services.dto;

import java.time.LocalDateTime;

// Kitap bilgilerini döndürmek için response DTO
public class BookResponse {
    public Long id;
    public String title;
    public String author;
    public String isbn;
    public Long categoryId;
    public Integer stockQuantity;
    public Integer availableQuantity;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}

