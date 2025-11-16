package com.turkcell.category_services.dto;

import java.time.LocalDateTime;

// Kategori bilgilerini döndürmek için response DTO
public class CategoryResponse {
    public Long id;
    public String name;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}

