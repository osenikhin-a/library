package com.culture.library.controller;

import com.culture.library.DTO.BookDTO;
import com.culture.library.domain.Book;
import com.culture.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/apibooks/v1")
public class BookController {
    private final BookService bookService;

    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooksDTO();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books/{isbn}")
    public BookDTO getBookByISBN(@PathVariable long isbn){
        return bookService.getBookByISBN(isbn);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/books")
    public BookDTO createBook(@RequestBody Book book){
        return bookService.saveBookAndReturnDTO(book);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/books/{isbn}")
    public BookDTO updateBook(@PathVariable long isbn){
        return bookService.deleteBook(isbn);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/books/{isbn}")
    public BookDTO updateBook(@PathVariable long isbn, @RequestBody Book book){
        return bookService.updateBook(book, isbn);
    }
}
