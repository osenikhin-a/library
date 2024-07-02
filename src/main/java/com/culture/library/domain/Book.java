package com.culture.library.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity(name = "book")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_books",
                joinColumns = {@JoinColumn(name = "book_isbn")},
                inverseJoinColumns = {@JoinColumn(name = "author_uuid")})
    private Set<Author> authors;

    @Id
    @Column(name = "isbn")
    private long isbn;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn && Objects.equals(authors, book.authors) && Objects.equals(date, book.date) && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authors, isbn, date, name);
    }
}
