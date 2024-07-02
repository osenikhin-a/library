package com.culture.library.service;

import com.culture.library.DTO.BookDTO;
import com.culture.library.domain.Author;
import com.culture.library.domain.Book;
import com.culture.library.repository.BookRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private  AuthorService authorService;

    private final Author authorPushkin = new Author("Александр", "Сергеевич", "Пушкин",
            LocalDate.of(1799, Month.JUNE, 6), LocalDate.of(1837, Month.FEBRUARY, 10),
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<Book>());
    private final Author authorTutchev = new Author("Фёдор", "Иванович", "Тютчев",
            LocalDate.of(1803, Month.DECEMBER, 5), LocalDate.of(1873, Month.JULY, 27),
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<Book>());

    private final Book bookRuslanILudmila = new Book(Set.of(authorPushkin), 123456789,
            LocalDate.of(2001, Month.JANUARY, 15), "Руслан и Людмила");

    private final Book bookStihiDetyam = new Book(Set.of(authorTutchev), 987654321,
            LocalDate.of(2015, Month.SEPTEMBER, 22), "Стихи детям");

    @Test
    void emptyDTO(){
        BookDTO bookDTO = bookService.getEmptyDTO();
        Assertions.assertEquals(bookDTO.getAuthors().length, 0);
        Assertions.assertEquals(bookDTO.getIsbn(), 0);
        Assertions.assertNull(bookDTO.getDate());
        Assertions.assertEquals(bookDTO.getName(), "");
    }

    @Test
    void saveBook(){
        Mockito.when(authorService.seveAuthor(authorPushkin))
                .thenReturn(authorPushkin);
        BookDTO bookDTO = bookService.saveBookAndReturnDTO(bookRuslanILudmila);
        Assertions.assertEquals(bookDTO.getAuthors().length, 1);
        Assertions.assertEquals(bookDTO.getAuthors()[0],
                authorPushkin.getName() + " " + authorPushkin.getPatronymic() + " " + authorPushkin.getSurname());
        Assertions.assertEquals(bookDTO.getIsbn(), bookRuslanILudmila.getIsbn());
        Assertions.assertEquals(bookDTO.getDate(), bookRuslanILudmila.getDate().toString());
        Assertions.assertEquals(bookDTO.getName(), bookRuslanILudmila.getName());
    }

    @Test
    void updateBook(){
        Mockito.when(bookRepo.findFirstByIsbn(bookRuslanILudmila.getIsbn()))
                .thenReturn(bookRuslanILudmila);
//        Mockito.when(authorService.seveAuthor(authorTutchev))
//                .thenReturn(authorTutchev);
        BookDTO bookDTO = bookService.updateBook(bookStihiDetyam, bookRuslanILudmila.getIsbn());
        Assertions.assertEquals(bookDTO.getAuthors().length, 1);
        Assertions.assertEquals(bookDTO.getAuthors()[0],
                authorTutchev.getName() + " " + authorTutchev.getPatronymic() + " " + authorTutchev.getSurname());
        Assertions.assertEquals(bookDTO.getIsbn(), bookRuslanILudmila.getIsbn());
        Assertions.assertEquals(bookDTO.getDate(), bookStihiDetyam.getDate().toString());
        Assertions.assertEquals(bookDTO.getName(), bookStihiDetyam.getName());
    }

    @Test
    void convertEntityToDTO(){
        BookDTO bookDTO = bookService.convertEntityToDTO(bookRuslanILudmila);
        Assertions.assertEquals(bookDTO.getAuthors().length, 1);
        Assertions.assertEquals(bookDTO.getAuthors()[0],
                authorPushkin.getName() + " " + authorPushkin.getPatronymic() + " " + authorPushkin.getSurname());
        Assertions.assertEquals(bookDTO.getIsbn(), bookRuslanILudmila.getIsbn());
        Assertions.assertEquals(bookDTO.getDate(), bookRuslanILudmila.getDate().toString());
        Assertions.assertEquals(bookDTO.getName(), bookRuslanILudmila.getName());
    }
}
