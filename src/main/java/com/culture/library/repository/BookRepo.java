package com.culture.library.repository;

import com.culture.library.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepo extends CrudRepository<Book, Long> {
    Book findFirstByIsbn(Long isbn);
}
