package com.turkcell.book_services.service;

import com.turkcell.book_services.dto.BookRequest;
import com.turkcell.book_services.dto.BookResponse;

import java.util.List;

// Kitap işlemleri için service interface'i
public interface BookService {
    // Yeni kitap oluştur
    BookResponse createBook(BookRequest req);

    // Kitap bilgilerini güncelle
    BookResponse updateBook(Long id, BookRequest req);

    // ID'ye göre kitap getir
    BookResponse getBookById(Long id);

    // Tüm kitapları listele
    List<BookResponse> getAllBooks();

    // Kategoriye göre kitapları getir
    List<BookResponse> getBooksByCategoryId(Long categoryId);

    // Başlığa göre kitap ara
    List<BookResponse> searchBooksByTitle(String title);

    // Yazar adına göre kitap ara
    List<BookResponse> searchBooksByAuthor(String author);

    // Mevcut kitapları getir
    List<BookResponse> getAvailableBooks();

    // Kitap stok miktarını güncelle (ödünç alma/teslim işlemleri için)
    BookResponse updateAvailableQuantity(Long id, Integer quantity);

    // Kitabı sil
    void deleteBook(Long id);
}

