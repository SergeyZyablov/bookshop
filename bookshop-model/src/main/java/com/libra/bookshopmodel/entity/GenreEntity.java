package com.libra.bookshopmodel.entity;

import com.libra.bookshopmodel.dto.genre.GenreDto;
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
@Table(name = "genre")
public class GenreEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_generator")
    @SequenceGenerator(
            name = "genre_id_generator",
            sequenceName = "genre_id_seq",
            schema = "bookshop",
            allocationSize = 1)
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "genre_name")
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<BookEntity> books;

    public GenreDto toDto() {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(id);
        genreDto.setName(name);

        if (books != null) {
            List<Long> bookIds = books.stream().map(BookEntity::getId).toList();
            genreDto.setBookIds(bookIds);
        }

        return genreDto;
    }
}