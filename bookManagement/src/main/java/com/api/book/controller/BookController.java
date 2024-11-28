package com.api.book.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.book.entity.Book;
import com.api.book.service.BookService;

import jakarta.validation.Valid;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    
    

    // Get all books - Accessible by USER role
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> list = bookService.getAllBooks();
        if (list.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    // Get a single book by ID - Accessible by USER role
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(book));
    }

    // Add a new book - Accessible by USER role
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book b = null;
        try {
            b = this.bookService.addBook(book);
            return ResponseEntity.of(Optional.of(b));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete a book - Accessible by ADMIN role
    
    @DeleteMapping("/books/{bookId}") // This matches the pattern in SecurityConfig
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") int bookId) {
        try {
            this.bookService.deleteBook(bookId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update a book - Accessible by USER role
    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("bookId") int bookId) {
        try {
            this.bookService.updateBook(book, bookId);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
