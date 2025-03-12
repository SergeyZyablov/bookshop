package com.libra.bookshopmodel.dto.author;

import com.libra.bookshopmodel.dto.book.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private List<BookDto> books;

}
