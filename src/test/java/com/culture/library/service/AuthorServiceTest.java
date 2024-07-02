package com.culture.library.service;

import com.culture.library.DTO.AuthorDTO;
import com.culture.library.domain.Author;
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
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<>());
    private final Author authorForUpdate = new Author("Фёдор", "Иванович", "Тютчев",
            LocalDate.of(1803, Month.DECEMBER, 5), LocalDate.of(1873, Month.JULY, 27),
            "cc95da2a-7019-425f-af0c-0e85207db4a2", new HashSet<>());

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
    void convertEntityToDTO(){
        AuthorDTO authorDTO = authorService.convertEntityToDTO(author);

        Assertions.assertEquals(authorDTO.getName(), author.getName());
        Assertions.assertEquals(authorDTO.getPatronymic(), author.getPatronymic());
        Assertions.assertEquals(authorDTO.getSurname(), author.getSurname());
        Assertions.assertEquals(authorDTO.getBirthDate(), author.getBirthDate().toString());
        Assertions.assertEquals(authorDTO.getDeathDate(), author.getDeathDate().toString());
    }

    @Test
    void convertAllEntitiesToDTO(){
        List<AuthorDTO> authorDTOs = authorService.convertAllEntitesToDTO(List.of(author, authorForUpdate));

        Assertions.assertEquals(authorDTOs.size(), 2);

        Assertions.assertEquals(authorDTOs.get(0).getName(), author.getName());
        Assertions.assertEquals(authorDTOs.get(0).getPatronymic(), author.getPatronymic());
        Assertions.assertEquals(authorDTOs.get(0).getSurname(), author.getSurname());
        Assertions.assertEquals(authorDTOs.get(0).getBirthDate(), author.getBirthDate().toString());
        Assertions.assertEquals(authorDTOs.get(0).getDeathDate(), author.getDeathDate().toString());

        Assertions.assertEquals(authorDTOs.get(1).getName(), authorForUpdate.getName());
        Assertions.assertEquals(authorDTOs.get(1).getPatronymic(), authorForUpdate.getPatronymic());
        Assertions.assertEquals(authorDTOs.get(1).getSurname(), authorForUpdate.getSurname());
        Assertions.assertEquals(authorDTOs.get(1).getBirthDate(), authorForUpdate.getBirthDate().toString());
        Assertions.assertEquals(authorDTOs.get(1).getDeathDate(), authorForUpdate.getDeathDate().toString());
    }

    @Test
    void getAllAuthorsDTO(){
        Mockito.when(authorRepo.findAll())
                .thenReturn(List.of(author, authorForUpdate));

        List<AuthorDTO> authorDTOs = authorService.getAllAuthorsDTO();

        Assertions.assertEquals(authorDTOs.size(), 2);

        Mockito.verify(authorRepo).findAll();
    }

    @Test
    void getAuthorByUuid(){
        Mockito.when(authorRepo.findFirstByUuid(author.getUuid()))
                .thenReturn(author);

        AuthorDTO authorDTO = authorService.getAuthorByUuid(author.getUuid());
        AuthorDTO refAuthor = authorService.convertEntityToDTO(author);

        Assertions.assertEquals(authorDTO, refAuthor);

        Mockito.verify(authorRepo).findFirstByUuid(author.getUuid());
    }

    @Test
    void saveAuthor(){
        Mockito.when(authorRepo.findFirstByNameAndPatronymicAndSurnameAndBirthDateAndDeathDate(author.getName(),
                        author.getPatronymic(), author.getSurname(), author.getBirthDate(), author.getDeathDate()))
                .thenReturn(author);

        AuthorDTO authorDTO = authorService.saveAuthorAndReturnDTO(author);

        Assertions.assertEquals(authorDTO.getName(), author.getName());
        Assertions.assertEquals(authorDTO.getPatronymic(), author.getPatronymic());
        Assertions.assertEquals(authorDTO.getSurname(), author.getSurname());
        Assertions.assertEquals(authorDTO.getBirthDate(), author.getBirthDate().toString());
        Assertions.assertEquals(authorDTO.getDeathDate(), author.getDeathDate().toString());

        Mockito.verify(authorRepo).findFirstByNameAndPatronymicAndSurnameAndBirthDateAndDeathDate(author.getName(),
                author.getPatronymic(), author.getSurname(), author.getBirthDate(), author.getDeathDate());
    }

    @Test
    void deleteAuthor(){
        Mockito.when(authorRepo.findFirstByUuid(authorForUpdate.getUuid()))
                .thenReturn(authorForUpdate);

        AuthorDTO authorDTO = authorService.deleteAuthor(authorForUpdate.getUuid());
        AuthorDTO refAuthor = authorService.convertEntityToDTO(authorForUpdate);

        Assertions.assertEquals(authorDTO, refAuthor);

        Mockito.verify(authorRepo).findFirstByUuid(author.getUuid());
    }

    @Test
    void updateAuthor(){
        Mockito.when(authorRepo.findFirstByUuid(author.getUuid()))
                .thenReturn(author);

        AuthorDTO authorDTO = authorService.updateAuthor(authorForUpdate, author.getUuid());

        Assertions.assertEquals(authorDTO.getName(), authorForUpdate.getName());
        Assertions.assertEquals(authorDTO.getPatronymic(), authorForUpdate.getPatronymic());
        Assertions.assertEquals(authorDTO.getSurname(), authorForUpdate.getSurname());
        Assertions.assertEquals(authorDTO.getBirthDate(), authorForUpdate.getBirthDate().toString());
        Assertions.assertEquals(authorDTO.getDeathDate(), authorForUpdate.getDeathDate().toString());

        Mockito.verify(authorRepo).findFirstByUuid(author.getUuid());
    }
}
