package com.api.book.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name="books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
	@SequenceGenerator(name = "book_seq", sequenceName = "BOOKS_SEQ", allocationSize = 1)
	
    private int id;
	@NotBlank(message = "Title cannot be empty or blank")
    private String title;
	 @NotBlank(message = "Author cannot be empty or blank")
    private String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Book() {
        // Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}
}
