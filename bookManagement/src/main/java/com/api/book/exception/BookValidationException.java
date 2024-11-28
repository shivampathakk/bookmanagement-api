package com.api.book.exception;

public class BookValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L; // You can change this number as needed

    public BookValidationException(String message) {
        super(message);
    }
}
