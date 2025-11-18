package com.turkcell.book_services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// Kitap oluşturma ve güncelleme işlemleri için DTO
public class BookRequest {
    @NotBlank(message = "Kitap başlığı gereklidir")
    public String title;

    @NotBlank(message = "Yazar adı gereklidir")
    public String author;

    public String isbn;

    @NotNull(message = "Kategori ID gereklidir")
    public Long categoryId;

    @NotNull(message = "Stok miktarı gereklidir")
    @PositiveOrZero(message = "Stok miktarı negatif olamaz")
    public Integer stockQuantity;

    @PositiveOrZero(message = "Mevcut miktar negatif olamaz")
    public Integer availableQuantity;
}

