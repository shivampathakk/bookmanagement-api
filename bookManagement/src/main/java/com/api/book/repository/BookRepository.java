package com.api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
    // No need to define findById again, JpaRepository provides it
}
