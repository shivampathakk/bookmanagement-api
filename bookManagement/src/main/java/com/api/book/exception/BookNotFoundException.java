package com.api.book.exception;

public class BookNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Add a serial version UID

    public BookNotFoundException(String message) {
        super(message);
    }
}
