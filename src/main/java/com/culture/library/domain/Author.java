package com.culture.library.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity(name = "author")
@RequiredArgsConstructor
@Getter
@Setter
public class Author {
    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "death_date")
    private LocalDate deathDate;

    @Id
    @Column(name = "uuid")
    private String uuid;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_books",
            joinColumns = {@JoinColumn(name = "author_uuid")},
            inverseJoinColumns = {@JoinColumn(name = "book_isbn")})
    private Set<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) && Objects.equals(patronymic, author.patronymic) && Objects.equals(surname, author.surname) && Objects.equals(birthDate, author.birthDate) && Objects.equals(deathDate, author.deathDate) && Objects.equals(uuid, author.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, patronymic, surname, birthDate, deathDate, uuid);
    }
}
