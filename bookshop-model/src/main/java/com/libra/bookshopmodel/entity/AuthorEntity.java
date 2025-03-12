package com.libra.bookshopmodel.entity;

import com.libra.bookshopmodel.dto.author.AuthorDto;
import com.libra.bookshopmodel.dto.book.BookDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class AuthorEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(
            name = "author_id_generator",
            sequenceName = "author_id_seq",
            schema = "bookshop",
            allocationSize = 1)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books;

    public AuthorDto toDto() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        authorDto.setFirstName(firstName);
        authorDto.setLastName(lastName);
        if (books != null) {
            List<BookDto> booksDto = books.stream().map(BookEntity::toDto).toList();
            authorDto.setBooks(booksDto);
        }
        return authorDto;
    }
}
