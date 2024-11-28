package com.api.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.book.entity.Book;
import com.api.book.repository.BookRepository;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService; // Assuming the service class is named BookService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testBookWithIdIsPresent() {
        // Create a book and set its ID
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        // Mock behavior: Return an Optional of the Book when finding by ID
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Assert that the book with ID 1 exists
        Optional<Book> foundBook = Optional.ofNullable(bookService.getBookById(1));
        assertTrue(foundBook.isPresent(), "Book with ID 1 should exist");
        assertEquals(1, foundBook.get().getId(), "The book ID should be 1");
    }


    @Test
    public void testGetAllBooks() {
        // Mock behavior: Return a list of books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Fetch all books
        List<Book> books = bookService.getAllBooks();

        // Assert that the list is not empty
        assertFalse(books.isEmpty(), "The book list should not be empty.");
    }

    @Test
    public void testAddBook() {
        // Create a new book object
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        // Mock behavior: Return the same book when saved
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Save the book
        Book savedBook = bookService.addBook(book);

        // Assertions
        assertNotNull(savedBook, "The saved book should not be null.");
        assertEquals("Test Book", savedBook.getTitle(), "The book title should match.");
        assertEquals("Test Author", savedBook.getAuthor(), "The book author should match.");
    }

    @Test
    public void testDeleteBook() {
        // Step 1: Create a new book
        Book book = new Book();
        book.setTitle("Book to Delete");
        book.setAuthor("Author to Delete");

        // Mock behavior: Return the saved book with an ID
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // Save the book
        Book savedBook = bookService.addBook(book);

        // Step 2: Delete the book by ID
        bookService.deleteBook(savedBook.getId());

        // Step 3: Verify the delete operation was called
        verify(bookRepository).deleteById(savedBook.getId());
    }

    @Test
    public void testUpdateBook() {
        // Step 1: Create and mock a book retrieval
        Book originalBook = new Book();
        originalBook.setTitle("Original Title");
        originalBook.setAuthor("Original Author");
        originalBook.setId(1);

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(originalBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Step 2: Update the book
        Book savedBook = bookService.updateBook(updatedBook, 0);

        // Step 3: Verify the update
        assertNotNull(savedBook, "The updated book should not be null.");
        assertEquals("Updated Title", savedBook.getTitle(), "The title should be updated.");
        assertEquals("Updated Author", savedBook.getAuthor(), "The author should be updated.");
    }
}
