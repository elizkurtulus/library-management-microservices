package com.turkcell.category_services.service;

import com.turkcell.category_services.dto.CategoryRequest;
import com.turkcell.category_services.dto.CategoryResponse;

import java.util.List;

// Kategori işlemleri için service interface'i
public interface CategoryService {
    // Yeni kategori oluştur
    CategoryResponse createCategory(CategoryRequest req);

    // Kategori bilgilerini güncelle
    CategoryResponse updateCategory(Long id, CategoryRequest req);

    // ID'ye göre kategori getir
    CategoryResponse getCategoryById(Long id);

    // Kategori adına göre kategori getir
    CategoryResponse getCategoryByName(String name);

    // Tüm kategorileri listele
    List<CategoryResponse> getAllCategories();

    // Kategoriyi sil
    void deleteCategory(Long id);
}

