package com.BookStoreApplication.service;

import com.BookStoreApplication.dto.BookDTO;
import com.BookStoreApplication.exception.BookStoreException;
import com.BookStoreApplication.model.Book;
import com.BookStoreApplication.repository.BookStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{

    @Autowired
    BookStoreRepository bookStoreRepository;

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book(bookDTO);
          return  bookStoreRepository.save(newBook);

    }

    @Override
    public Book getBookDataById(int BookId) {
        Optional<Book> getBook=bookStoreRepository.findById(BookId);
        if(getBook.isPresent()){
            return getBook.get();

        }
        throw new BookStoreException("Book Store Details for id not found");

    }

    @Override
    public List<Book> getAllBookData() {
        List<Book> getBooks=bookStoreRepository.findAll();
        return getBooks;
    }

    @Override
    public Book updateRecordById(Integer BookId, BookDTO bookDTO) {

        Optional<Book> updateBook = bookStoreRepository.findById(BookId);
        if (updateBook.isPresent()) {
            Book updateUser = new Book(BookId, bookDTO);
            bookStoreRepository.save(updateUser);
            return updateUser;

        } else {
            throw new BookStoreException("Book record does not found");

        }
    }


    @Override
    public Object deleteRecordByToken(int BookId) {
        Optional<Book> newBook = bookStoreRepository.findById(BookId);
        if (newBook.isPresent()) {
            bookStoreRepository.deleteById(BookId);

        } else {
            throw new BookStoreException("Book record does not found");
        }
        return null;
    }


    @Override
    public List <Book> getBookByName(String bookName) {
        
        List<Book> listOfBooks = bookStoreRepository.findByBookName(bookName);
        if(listOfBooks.isEmpty()) {
        	throw new BookStoreException("No book with this  name sorry !!");
        }
        return listOfBooks;
    }

    @Override
    public List<Book> sortedListOfBooksInAscendingOrder() {
        List<Book> getSortedList=  bookStoreRepository.getSortedListOfBooksInAsc();
        return getSortedList;
    }

    @Override
    public List<Book> sortedListOfBooksInDescendingOrder() {
        List<Book> getSortedListInDesc=  bookStoreRepository.getSortedListOfBooksInDesc();
        return getSortedListInDesc;
    }

    @Override
    public Book updateQuantity(Integer id, Integer quantity) {
        Optional<Book> book = bookStoreRepository.findById(id);
        if(book.isPresent()) {
            book.get().setQuantity(quantity);
            bookStoreRepository.save(book.get());
            return book.get();

        }
        else {
            throw new BookStoreException("Book Record doesn't exists");
        }
    }


}
