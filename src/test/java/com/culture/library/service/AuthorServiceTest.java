package com.culture.library.service;

import com.culture.library.DTO.AuthorDTO;
import com.culture.library.domain.Author;
import com.culture.library.domain.Book;
import com.culture.library.repository.AuthorRepo;
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

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepo authorRepo;

    private final Author author = new Author("Александр", "Сергеевич", "Пушкин",
            LocalDate.of(1799, Month.JUNE, 6), LocalDate.of(1837, Month.FEBRUARY, 10),
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<Book>());
    private final Author authorForUpdate = new Author("Фёдор", "Иванович", "Тютчев",
            LocalDate.of(1803, Month.DECEMBER, 5), LocalDate.of(1873, Month.JULY, 27),
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<Book>());

    @Test
    void emptyDTO(){
        AuthorDTO authorDTO = authorService.getEmptyDTO();
        Assertions.assertEquals(authorDTO.getName(), "");
        Assertions.assertEquals(authorDTO.getPatronymic(), "");
        Assertions.assertEquals(authorDTO.getSurname(), "");
        Assertions.assertEquals(authorDTO.getUuid(), "");
        Assertions.assertEquals(authorDTO.getDeathDate(), "");
    }

    @Test
    void saveAuthor(){
        Mockito.when(authorRepo.findFirstByNameAndPatronymicAndSurnameAndBirthDateAndDeathDate(author.getName(),
                        author.getPatronymic(), author.getSurname(), author.getBirthDate(), author.getDeathDate()))
                .thenReturn(author);
        AuthorDTO authorDTO = authorService.saveAuthorAndReturnDTO(author);

        Assertions.assertEquals(authorDTO.getName(), "Александр");
        Assertions.assertEquals(authorDTO.getPatronymic(), "Сергеевич");
        Assertions.assertEquals(authorDTO.getSurname(), "Пушкин");
        Assertions.assertEquals(authorDTO.getBirthDate(), LocalDate.of(1799, Month.JUNE, 6).toString());
        Assertions.assertEquals(authorDTO.getDeathDate(), LocalDate.of(1837, Month.FEBRUARY, 10).toString());
    }

    @Test
    void updateAuthor(){
        Mockito.when(authorRepo.findFirstByUuid(author.getUuid()))
                .thenReturn(author);
        AuthorDTO authorDTO = authorService.updateAuthor(authorForUpdate, author.getUuid());

        Assertions.assertEquals(authorDTO.getName(), "Фёдор");
        Assertions.assertEquals(authorDTO.getPatronymic(), "Иванович");
        Assertions.assertEquals(authorDTO.getSurname(), "Тютчев");
        Assertions.assertEquals(authorDTO.getBirthDate(), LocalDate.of(1803, Month.DECEMBER, 5).toString());
        Assertions.assertEquals(authorDTO.getDeathDate(), LocalDate.of(1873, Month.JULY, 27).toString());
    }

    @Test
    void convertEntityToDTO(){
        AuthorDTO authorDTO = authorService.convertEntityToDTO(author);

        Assertions.assertEquals(authorDTO.getName(), "Александр");
        Assertions.assertEquals(authorDTO.getPatronymic(), "Сергеевич");
        Assertions.assertEquals(authorDTO.getSurname(), "Пушкин");
        Assertions.assertEquals(authorDTO.getBirthDate(), LocalDate.of(1799, Month.JUNE, 6).toString());
        Assertions.assertEquals(authorDTO.getDeathDate(), LocalDate.of(1837, Month.FEBRUARY, 10).toString());

        List<AuthorDTO> authorDTOList = authorService.convertAllEntitesToDTO(List.of(author, authorForUpdate));
        Assertions.assertEquals(authorDTOList.size(), 2);
    }
}
