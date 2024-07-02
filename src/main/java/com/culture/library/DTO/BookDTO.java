package com.culture.library.DTO;

import lombok.Data;

@Data
public class BookDTO {
    private String[] authors;
    private long isbn;
    private String date;
    private String name;
}
