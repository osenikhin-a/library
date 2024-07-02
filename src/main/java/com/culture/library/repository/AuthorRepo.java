package com.culture.library.repository;

import com.culture.library.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepo extends CrudRepository<Author, String> {
    Author findFirstByUuid(String uuid);
    List<Author> findByNameAndPatronymicAndSurname(String name, String patronymic, String surname);
    Author findFirstByNameAndPatronymicAndSurnameAndBirthDateAndDeathDate(String name, String patronymic, String surname, LocalDate birthDate, LocalDate deathDate);
}
