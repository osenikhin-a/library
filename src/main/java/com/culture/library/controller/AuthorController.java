package com.culture.library.controller;

import com.culture.library.DTO.AuthorDTO;
import com.culture.library.domain.Author;
import com.culture.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/apiauthors/v1")
public class AuthorController {
    private final AuthorService authorService;

    @RequestMapping(method = RequestMethod.GET, value = "/authors")
    public List<AuthorDTO> getAllAuthors(){
        return authorService.getAllAuthorsDTO();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/authors/{uuid}")
    public AuthorDTO getAuthorByUuid(@PathVariable String uuid){
        return authorService.getAuthorByUuid(uuid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authors")
    public AuthorDTO createAuthor(@RequestBody Author author){
        return authorService.saveAuthorAndReturnDTO(author);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/authors/{uuid}")
    public AuthorDTO deleteAuthor(@PathVariable String uuid){
        return authorService.deleteAuthor(uuid);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/authors/{uuid}")
    public AuthorDTO updateAuthor(@PathVariable String uuid, @RequestBody Author author){
        return authorService.updateAuthor(author, uuid);
    }
}
