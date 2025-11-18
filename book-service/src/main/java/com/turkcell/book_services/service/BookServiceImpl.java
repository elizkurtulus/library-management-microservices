package com.turkcell.book_services.service;

import com.turkcell.book_services.dto.BookRequest;
import com.turkcell.book_services.dto.BookResponse;
import com.turkcell.book_services.entity.Book;
import com.turkcell.book_services.repository.BookRepository;
import com.turkcell.book_services.client.CategoryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Kitap işlemlerini yürüten service implementasyonu
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryClient categoryClient;

    public BookServiceImpl(BookRepository bookRepository, CategoryClient categoryClient) {
        this.bookRepository = bookRepository;
        this.categoryClient = categoryClient;
    }

    @Override
    @Transactional
    public BookResponse createBook(BookRequest req) {
        // ISBN numarası zaten varsa hata fırlat
        if (req.isbn != null && !req.isbn.isEmpty()) {
            bookRepository.findByIsbn(req.isbn).ifPresent(b -> {
                throw new IllegalStateException("Bu ISBN numarasına sahip kitap zaten mevcut!");
            });
        }

        // Kategori ID'sinin geçerli olup olmadığını kontrol et
        validateCategoryExists(req.categoryId);

        Book book = new Book();
        book.setTitle(req.title);
        book.setAuthor(req.author);
        book.setIsbn(req.isbn);
        book.setCategoryId(req.categoryId);
        book.setStockQuantity(req.stockQuantity);
        book.setAvailableQuantity(req.availableQuantity != null ? req.availableQuantity : req.stockQuantity);

        return mapToResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookRequest req) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitap bulunamadı!"));

        // ISBN güncelleniyorsa kontrol et
        if (req.isbn != null && !req.isbn.isEmpty() && !req.isbn.equals(book.getIsbn())) {
            bookRepository.findByIsbn(req.isbn).ifPresent(b -> {
                throw new IllegalStateException("Bu ISBN numarasına sahip başka bir kitap zaten mevcut!");
            });
        }

        // Kategori ID güncelleniyorsa kontrol et
        if (req.categoryId != null && !req.categoryId.equals(book.getCategoryId())) {
            validateCategoryExists(req.categoryId);
        }

        if (req.title != null)
            book.setTitle(req.title);
        if (req.author != null)
            book.setAuthor(req.author);
        if (req.isbn != null)
            book.setIsbn(req.isbn);
        if (req.categoryId != null)
            book.setCategoryId(req.categoryId);
        if (req.stockQuantity != null)
            book.setStockQuantity(req.stockQuantity);
        if (req.availableQuantity != null)
            book.setAvailableQuantity(req.availableQuantity);

        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitap bulunamadı!"));
        return mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByAvailableQuantityGreaterThan(0).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookResponse updateAvailableQuantity(Long id, Integer quantity) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitap bulunamadı!"));

        if (quantity < 0)
            throw new IllegalArgumentException("Mevcut miktar negatif olamaz!");

        if (quantity > book.getStockQuantity())
            throw new IllegalStateException("Mevcut miktar stok miktarını aşamaz!");

        book.setAvailableQuantity(quantity);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id))
            throw new IllegalArgumentException("Kitap bulunamadı!");
        bookRepository.deleteById(id);
    }

    // Category service'den kategori var mı kontrol et
    private void validateCategoryExists(Long categoryId) {
        try {
            categoryClient.getCategoryById(categoryId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Geçersiz kategori ID!");
        }
    }

    // Entity'yi Response DTO'ya dönüştür
    private BookResponse mapToResponse(Book book) {
        BookResponse res = new BookResponse();
        res.id = book.getId();
        res.title = book.getTitle();
        res.author = book.getAuthor();
        res.isbn = book.getIsbn();
        res.categoryId = book.getCategoryId();
        res.stockQuantity = book.getStockQuantity();
        res.availableQuantity = book.getAvailableQuantity();
        res.createdAt = book.getCreatedAt();
        res.updatedAt = book.getUpdatedAt();
        return res;
    }
}

