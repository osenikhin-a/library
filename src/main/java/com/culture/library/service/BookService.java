package com.culture.library.service;

import com.culture.library.DTO.BookDTO;
import com.culture.library.domain.Author;
import com.culture.library.domain.Book;
import com.culture.library.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorService authorService;

    // ---> DTO
    public BookDTO getEmptyDTO(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthors(new String[]{""});
        bookDTO.setIsbn(0);
        bookDTO.setDate("");
        bookDTO.setName("");
        return  bookDTO;
    }

    public BookDTO convertEntityToDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthors(book.getAuthors().stream()
                .map(author -> author.getName() + " " + author.getPatronymic() + " " + author.getSurname()).toArray(String[]::new));
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setDate(book.getDate().toString());
        bookDTO.setName(book.getName());
        return  bookDTO;
    }

    List<BookDTO> convertAllEntitysToDTO(Iterable<Book> books){
        List<BookDTO> bookDTOs = new ArrayList<>();
        for(Book book : books){
            bookDTOs.add(convertEntityToDTO(book));
        }
        return bookDTOs;
    }
    // <--- DTO

    // ---> Get
    public Iterable<Book> getAllBooks(){
        return bookRepo.findAll();
    }

    public List<BookDTO> getAllBooksDTO(){
        return convertAllEntitysToDTO(getAllBooks());
    }

    public BookDTO getBookByISBN(long isbn){
        Book book = bookRepo.findFirstByIsbn(isbn);
        if(book != null) {
            return convertEntityToDTO(book);
        }
        return getEmptyDTO();
    }
    // <--- Get

    // --> Post
    @Transactional
    public Book saveBook(Book book) {
        book.setAuthors(book.getAuthors().stream()
                .map(authorService::seveAuthor).collect(Collectors.toSet()));
        bookRepo.save(book);
        return book;
    }

    @Transactional
    public BookDTO saveBookAndReturnDTO(Book book){
        return convertEntityToDTO(saveBook(book));
    }
    // <--- Post

    // ---> Delete
    @Transactional
    public BookDTO deleteBook(Long isbn){
        Book currentBook = bookRepo.findFirstByIsbn(isbn);
        if(currentBook != null){
            bookRepo.delete(currentBook);
            return convertEntityToDTO(currentBook);
        }
        return getEmptyDTO();
    }
    // <--- Delete

    // ---> Put
    @Transactional
    public BookDTO updateBook(Book book, Long isbn){
        Book currentBook = bookRepo.findFirstByIsbn(isbn);
        if(currentBook != null){
            currentBook.setName(book.getName());
            currentBook.setDate(book.getDate());
            currentBook.setAuthors(book.getAuthors());
            bookRepo.save(currentBook);
            return convertEntityToDTO(currentBook);
        }
        return convertEntityToDTO(book);
    }
    // <--- Put
}
