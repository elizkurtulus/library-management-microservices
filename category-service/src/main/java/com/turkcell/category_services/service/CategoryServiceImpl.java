package com.turkcell.category_services.service;

import com.turkcell.category_services.dto.CategoryRequest;
import com.turkcell.category_services.dto.CategoryResponse;
import com.turkcell.category_services.entity.Category;
import com.turkcell.category_services.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Kategori işlemlerini yürüten service implementasyonu
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest req) {
        // Kategori adı zaten varsa hata fırlat
        if (categoryRepository.existsByName(req.name)) {
            throw new IllegalStateException("Bu kategori adı zaten mevcut!");
        }

        Category category = new Category();
        category.setName(req.name);
        category.setDescription(req.description);

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));

        // Kategori adı güncelleniyorsa kontrol et
        if (req.name != null && !req.name.equals(category.getName())) {
            if (categoryRepository.existsByName(req.name)) {
                throw new IllegalStateException("Bu kategori adı zaten başka bir kategori tarafından kullanılıyor!");
            }
        }

        if (req.name != null)
            category.setName(req.name);
        if (req.description != null)
            category.setDescription(req.description);

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));
        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id))
            throw new IllegalArgumentException("Kategori bulunamadı!");
        categoryRepository.deleteById(id);
    }

    // Entity'yi Response DTO'ya dönüştür
    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse res = new CategoryResponse();
        res.id = category.getId();
        res.name = category.getName();
        res.description = category.getDescription();
        res.createdAt = category.getCreatedAt();
        res.updatedAt = category.getUpdatedAt();
        return res;
    }
}

