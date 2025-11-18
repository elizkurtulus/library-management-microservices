package com.turkcell.book_services.client;

import com.turkcell.book_services.dto.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Category service ile iletişim için Feign client
@FeignClient(name = "category-service", path = "/api/v1/categories")
public interface CategoryClient {
    // ID'ye göre kategori bilgisi getir
    @GetMapping("/{id}")
    CategoryResponse getCategoryById(@PathVariable Long id);
}

