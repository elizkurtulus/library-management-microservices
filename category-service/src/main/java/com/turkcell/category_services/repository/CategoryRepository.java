package com.turkcell.category_services.repository;

import com.turkcell.category_services.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Kategori işlemleri için CRUD ve özel sorgulu repository interface'i
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Kategori adına göre kategori getir
    Optional<Category> findByName(String name);

    // Kategori adına göre kategori var mı kontrol et
    boolean existsByName(String name);
}

