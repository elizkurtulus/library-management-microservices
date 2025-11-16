package com.turkcell.book_services.controller;

import com.turkcell.book_services.dto.BookRequest;
import com.turkcell.book_services.dto.BookResponse;
import com.turkcell.book_services.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Kitap işlemleri için REST API endpoint'leri
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Yeni kitap oluştur
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest req) {
        return ResponseEntity.ok(bookService.createBook(req));
    }

    // Kitap bilgilerini güncelle
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
            @Valid @RequestBody BookRequest req) {
        return ResponseEntity.ok(bookService.updateBook(id, req));
    }

    // ID'ye göre kitap getir
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // Tüm kitapları listele
    @GetMapping
    public Object getAllBooks(@RequestParam(required = false) String name) {
        // Geriye uyumluluk için: Loan-service'in kullandığı eski endpoint
        if (name != null) {
            return "Merhaba " + name + " , Book-Service";
        }
        return bookService.getAllBooks();
    }

    // Kategoriye göre kitapları getir
    @GetMapping("/category/{categoryId}")
    public List<BookResponse> getBooksByCategory(@PathVariable Long categoryId) {
        return bookService.getBooksByCategoryId(categoryId);
    }

    // Başlığa göre kitap ara
    @GetMapping("/search/title")
    public List<BookResponse> searchBooksByTitle(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }

    // Yazar adına göre kitap ara
    @GetMapping("/search/author")
    public List<BookResponse> searchBooksByAuthor(@RequestParam String author) {
        return bookService.searchBooksByAuthor(author);
    }

    // Mevcut kitapları getir
    @GetMapping("/available")
    public List<BookResponse> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    // Kitap stok miktarını güncelle
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<BookResponse> updateAvailableQuantity(@PathVariable Long id,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(bookService.updateAvailableQuantity(id, quantity));
    }

    // Kitabı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
