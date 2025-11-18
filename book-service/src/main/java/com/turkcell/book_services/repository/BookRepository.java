package com.turkcell.book_services.repository;

import com.turkcell.book_services.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Kitap işlemleri için CRUD ve özel sorgulu repository interface'i
public interface BookRepository extends JpaRepository<Book, Long> {
    // Kategori ID'sine göre kitapları getir
    List<Book> findByCategoryId(Long categoryId);

    // ISBN numarasına göre kitap getir
    Optional<Book> findByIsbn(String isbn);

    // Başlığa göre kitapları ara
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Yazar adına göre kitapları ara
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Mevcut kitapları getir (availableQuantity > 0)
    List<Book> findByAvailableQuantityGreaterThan(Integer quantity);
}

