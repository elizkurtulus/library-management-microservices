package com.turkcell.book_services.dto;

import java.time.LocalDateTime;

// Category service'den gelen kategori bilgisi için DTO
public class CategoryResponse {
    public Long id;
    public String name;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}

