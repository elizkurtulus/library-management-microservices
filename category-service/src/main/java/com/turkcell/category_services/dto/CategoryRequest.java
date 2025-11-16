package com.turkcell.category_services.dto;

import jakarta.validation.constraints.NotBlank;

// Kategori oluşturma ve güncelleme işlemleri için DTO
public class CategoryRequest {
    @NotBlank(message = "Kategori adı gereklidir")
    public String name;

    public String description;
}

