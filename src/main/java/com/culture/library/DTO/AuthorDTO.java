package com.culture.library.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDTO {
    private String name;
    private String patronymic;
    private String surname;
    private String birthDate;
    private String deathDate;
    private String uuid;
}
