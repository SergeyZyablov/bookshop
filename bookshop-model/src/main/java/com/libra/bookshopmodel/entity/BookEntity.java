package com.libra.bookshopmodel.entity;

import com.libra.bookshopmodel.dto.book.BookDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(
            name = "book_id_generator",
            sequenceName = "book_id_seq",
            schema = "bookshop",
            allocationSize = 1)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "book_price")
    private BigDecimal price;

    @ManyToOne
    private AuthorEntity author;

    @ManyToOne
    private GenreEntity genre;

    public BookDto toDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setName(name);
        bookDto.setPrice(price);

        if (author != null) {
            bookDto.setAuthorName(author.getFirstName() + " " + author.getLastName());
        }

        bookDto.setGenreName(Objects.requireNonNullElse(genre.getName(), ""));
        return bookDto;
    }

}
