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
import java.util.List;
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
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<>());
    private final Author authorTutchev = new Author("Фёдор", "Иванович", "Тютчев",
            LocalDate.of(1803, Month.DECEMBER, 5), LocalDate.of(1873, Month.JULY, 27),
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<>());

    private final Book bookRuslanILudmila = new Book(Set.of(authorPushkin), 123456789,
            LocalDate.of(2001, Month.JANUARY, 15), "Руслан и Людмила");

    private final Book bookStihiDetyam = new Book(Set.of(authorTutchev), 987654321,
            LocalDate.of(2015, Month.SEPTEMBER, 22), "Стихи детям");

    @Test
    void emptyDTO(){
        BookDTO bookDTO = bookService.getEmptyDTO();

        Assertions.assertEquals(bookDTO.getAuthors().length, 1);
        Assertions.assertEquals(bookDTO.getIsbn(), 0);
        Assertions.assertEquals(bookDTO.getDate(), "");
        Assertions.assertEquals(bookDTO.getName(), "");
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

    @Test
    void convertAllEntitiesToDTO(){
        List<BookDTO> bookDTOs = bookService.convertAllEntitysToDTO(List.of(bookStihiDetyam, bookRuslanILudmila));

        Assertions.assertEquals(bookDTOs.size(), 2);

        Assertions.assertEquals(bookDTOs.get(0).getAuthors().length, 1);
        Assertions.assertEquals(bookDTOs.get(0).getAuthors()[0],
                authorTutchev.getName() + " " + authorTutchev.getPatronymic() + " " + authorTutchev.getSurname());
        Assertions.assertEquals(bookDTOs.get(0).getIsbn(), bookStihiDetyam.getIsbn());
        Assertions.assertEquals(bookDTOs.get(0).getDate(), bookStihiDetyam.getDate().toString());
        Assertions.assertEquals(bookDTOs.get(0).getName(), bookStihiDetyam.getName());

        Assertions.assertEquals(bookDTOs.get(1).getAuthors().length, 1);
        Assertions.assertEquals(bookDTOs.get(1).getAuthors()[0],
                authorPushkin.getName() + " " + authorPushkin.getPatronymic() + " " + authorPushkin.getSurname());
        Assertions.assertEquals(bookDTOs.get(1).getIsbn(), bookRuslanILudmila.getIsbn());
        Assertions.assertEquals(bookDTOs.get(1).getDate(), bookRuslanILudmila.getDate().toString());
        Assertions.assertEquals(bookDTOs.get(1).getName(), bookRuslanILudmila.getName());
    }

    @Test
    void getAllBooksDTO(){
        Mockito.when(bookRepo.findAll())
                .thenReturn(List.of(bookStihiDetyam, bookRuslanILudmila));

        List<BookDTO> bookDTOs = bookService.getAllBooksDTO();

        Assertions.assertEquals(bookDTOs.size(), 2);

        Mockito.verify(bookRepo).findAll();
    }

    @Test
    void getBookByISBN(){
        Mockito.when(bookRepo.findFirstByIsbn(bookRuslanILudmila.getIsbn()))
                .thenReturn(bookRuslanILudmila);

        BookDTO bookDTO = bookService.getBookByISBN(bookRuslanILudmila.getIsbn());
        BookDTO refBook = bookService.convertEntityToDTO(bookRuslanILudmila);

        Assertions.assertEquals(bookDTO, refBook);

        Mockito.verify(bookRepo).findFirstByIsbn(bookRuslanILudmila.getIsbn());
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

        Mockito.verify(authorService).seveAuthor(authorPushkin);
    }

    @Test
    void deleteBook(){
        Mockito.when(bookRepo.findFirstByIsbn(bookRuslanILudmila.getIsbn()))
                .thenReturn(bookRuslanILudmila);

        BookDTO bookDTO = bookService.deleteBook(bookRuslanILudmila.getIsbn());
        BookDTO refBook = bookService.convertEntityToDTO(bookRuslanILudmila);

        Assertions.assertEquals(bookDTO, refBook);

        Mockito.verify(bookRepo).findFirstByIsbn(bookRuslanILudmila.getIsbn());
    }

    @Test
    void updateBook(){
        Mockito.when(bookRepo.findFirstByIsbn(bookRuslanILudmila.getIsbn()))
                .thenReturn(bookRuslanILudmila);

        BookDTO bookDTO = bookService.updateBook(bookStihiDetyam, bookRuslanILudmila.getIsbn());

        Assertions.assertEquals(bookDTO.getAuthors().length, 1);
        Assertions.assertEquals(bookDTO.getAuthors()[0],
                authorTutchev.getName() + " " + authorTutchev.getPatronymic() + " " + authorTutchev.getSurname());
        Assertions.assertEquals(bookDTO.getIsbn(), bookRuslanILudmila.getIsbn());
        Assertions.assertEquals(bookDTO.getDate(), bookStihiDetyam.getDate().toString());
        Assertions.assertEquals(bookDTO.getName(), bookStihiDetyam.getName());

        Mockito.verify(bookRepo).findFirstByIsbn(bookRuslanILudmila.getIsbn());
    }
}
