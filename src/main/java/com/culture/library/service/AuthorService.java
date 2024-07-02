package com.culture.library.service;

import com.culture.library.DTO.AuthorDTO;
import com.culture.library.domain.Author;
import com.culture.library.repository.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepo authorRepo;

    // ---> DTO
    public AuthorDTO getEmptyDTO(){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("");
        authorDTO.setPatronymic("");
        authorDTO.setSurname("");
        authorDTO.setUuid("");
        return  authorDTO;
    }

    public AuthorDTO convertEntityToDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        if(author != null) {
            authorDTO.setName(author.getName());
            authorDTO.setPatronymic(author.getPatronymic());
            authorDTO.setSurname(author.getSurname());
            authorDTO.setBirthDate(author.getBirthDate());
            authorDTO.setDeathDate(author.getDeathDate());
            authorDTO.setUuid(author.getUuid());
        }
        return  authorDTO;
    }

    List<AuthorDTO> convertAllEntitysToDTO(Iterable<Author> authors){
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for(Author author : authors){
            authorDTOs.add(convertEntityToDTO(author));
        }
        return authorDTOs;
    }
    // <--- DTO

    // ---> Get
    public Iterable<Author> getAllAuthors(){
        return authorRepo.findAll();
    }

    public List<AuthorDTO> getAllAuthorsDTO(){
        return convertAllEntitysToDTO(getAllAuthors());
    }

    public AuthorDTO getAuthorByUuid(String uuid){
        Author author = authorRepo.findFirstByUuid(uuid);
        if(author != null){
            return convertEntityToDTO(author);
        }
        return getEmptyDTO();
    }
    // <--- Get

    // --> Post
    @Transactional
    public Author seveAuthor(Author author){
        Author currentAuthor = authorRepo.findFirstByNameAndPatronymicAndSurnameAndBirthDateAndDeathDate(author.getName(),
                author.getPatronymic(), author.getSurname(), author.getBirthDate(), author.getDeathDate());
        if(currentAuthor != null){
            return currentAuthor;
        }
        if(author.getUuid() == null || author.getUuid().isEmpty()){
            author.setUuid(UUID.randomUUID().toString());
        }
        authorRepo.save(author);
        return author;
    }

    @Transactional
    public AuthorDTO saveAuthorAndReturnDTO(Author author){
        return convertEntityToDTO(seveAuthor(author));
    }
    // <--- Post

    // ---> Delete
    @Transactional
    public AuthorDTO deleteAuthor(String uuid){
        Author currentAuthor = authorRepo.findFirstByUuid(uuid);
        if(currentAuthor != null){
            authorRepo.delete(currentAuthor);
            return convertEntityToDTO(currentAuthor);
        }
        return getEmptyDTO();
    }
    // <--- Delete

    // ---> Put
    @Transactional
    public AuthorDTO updateAuthor(Author author, String uuid){
        Author currentAuthor = authorRepo.findFirstByUuid(uuid);
        if(currentAuthor != null){
            currentAuthor.setName(author.getName());
            currentAuthor.setPatronymic(author.getPatronymic());
            currentAuthor.setSurname(author.getSurname());
            currentAuthor.setBirthDate(author.getBirthDate());
            currentAuthor.setDeathDate(author.getDeathDate());
            authorRepo.save(currentAuthor);
            return convertEntityToDTO(currentAuthor);
        }
        return getEmptyDTO();
    }
    // <--- Put
}
