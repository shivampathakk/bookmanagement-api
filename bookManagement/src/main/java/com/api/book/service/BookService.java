package com.api.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.book.entity.Book;
import com.api.book.repository.BookRepository;

@Service
public class BookService {

 /*   private static List<Book> list = new ArrayList<>();

    static {
        list.add(new Book(10, "Java for Beginners", "John Doe"));
        list.add(new Book(11, "Spring Boot Guide", "Jane Smith"));
        list.add(new Book(12, "Advanced Java", "Michael Johnson"));
    }  */
	@Autowired
	private BookRepository bookRepository;

    // Get all books
    public List<Book> getAllBooks() {
        List<Book>list=(List<Book>) this.bookRepository.findAll();
        return list;
    }

    // Get a single book by id
    public Book getBookById(int id) {
        Optional<Book> book = this.bookRepository.findById(id);
        return book.orElse(null); // return null if not found
    }
    //adding the book
    public Book addBook(Book b) {
    	Book result=this.bookRepository.save(b);
    	return result;
    }
    //delete book
    public void deleteBook(int bid) {
    	//list= list.stream().filter(book->book.getId()!=bid).collect(Collectors.toList());
    	bookRepository.deleteById(bid);
    }
    //update book
    public Book updateBook(Book book, int bookId) {
    /*	list=list.stream().map(b->{
    		if(b.getId()==bookId) {
    			b.setTitle(book.getTitle());
    			b.setAuthor(book.getAuthor());
    			
    		}
    		return b;
    	}).collect(Collectors.toList());*/
    	book.setId(bookId);
    	return bookRepository.save(book);
    	}
    }

