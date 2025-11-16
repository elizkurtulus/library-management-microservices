package com.turkcell.book_services.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

// Kitap bilgilerini tutan entity modeli
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Kitap başlığı gereklidir")
    @Column(nullable = false)
    private String title; // Kitap başlığı

    @NotBlank(message = "Yazar adı gereklidir")
    @Column(nullable = false)
    private String author; // Yazar adı

    @Column(unique = true)
    private String isbn; // Kitap ISBN numarası

    @NotNull(message = "Kategori ID gereklidir")
    @Column(nullable = false)
    private Long categoryId; // Kitabın ait olduğu kategori ID'si

    @NotNull(message = "Stok miktarı gereklidir")
    @PositiveOrZero(message = "Stok miktarı negatif olamaz")
    @Column(nullable = false)
    private Integer stockQuantity; // Toplam stok miktarı

    @NotNull(message = "Mevcut miktar gereklidir")
    @PositiveOrZero(message = "Mevcut miktar negatif olamaz")
    @Column(nullable = false)
    private Integer availableQuantity; // Şu an mevcut kitap sayısı

    private LocalDateTime createdAt; // Kitabın eklendiği tarih

    private LocalDateTime updatedAt; // Kitabın güncellendiği tarih

    // Kayıt oluşturulurken tarih atanır
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();
        if (this.availableQuantity == null && this.stockQuantity != null)
            this.availableQuantity = this.stockQuantity;
    }

    // Kayıt güncellenirken tarih güncellenir
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- GETTER ve SETTER metodları ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
