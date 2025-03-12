package com.libra.bookshopbook.services;

import com.libra.bookshopdata.repository.AuthorRepository;
import com.libra.bookshopdata.repository.BookRepository;
import com.libra.bookshopmodel.dto.author.AuthorCreateRequest;
import com.libra.bookshopmodel.dto.author.AuthorDto;
import com.libra.bookshopmodel.dto.author.AuthorUpdateRequest;
import com.libra.bookshopmodel.entity.AuthorEntity;
import com.libra.bookshopmodel.entity.BookEntity;
import com.libra.bookshopmodel.entity.GenreEntity;
import com.libra.bookshopmodel.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    public static final String AUTHOR_WITH_ID_NOT_FOUND = "Author with id 1 not found";
    private final Long validAuthorId = 1L;
    private final Long invalidAuthorId = 999L;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void testFindAllAuthors_thenReturnListOfAuthors() {
        AuthorEntity testAuthor = createTestAuthor();

        when(authorRepository.findAll()).thenReturn(List.of(testAuthor));

        List<AuthorDto> actualAuthors = authorService.findAllAuthors();

        verify(authorRepository, times(1)).findAll();
        assertThat(actualAuthors).hasSize(1).first().isEqualTo(testAuthor.toDto());
    }

    @Test
    void testFindAuthorById_withValidId_thenReturnAuthor() {
        AuthorEntity testAuthor = createTestAuthor();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));

        AuthorDto actualAuthor = authorService.findAuthorById(1L);

        verify(authorRepository, times(1)).findById(1L);
        assertThat(actualAuthor).isEqualTo(testAuthor.toDto());
    }

    @Test
    void testFindAuthorById_withInvalidId_thenRThrowNotFoundException() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> authorService.findAuthorById(1L));

        assertThat(exception).hasMessage(AUTHOR_WITH_ID_NOT_FOUND);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateAuthor_withValidAuthor_thenReturnAuthor() {
        AuthorEntity testAuthor = createTestAuthor();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testAuthor.getBooks().get(0)));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(testAuthor.getBooks().get(1)));
        when(authorRepository.save(any())).thenReturn(testAuthor);

        AuthorDto actualAuthor = authorService.createAuthor(
                new AuthorCreateRequest("John", "Doe", List.of(1L, 2L)));

        verify(authorRepository, times(1)).save(any());
        verify(bookRepository, times(2)).findById(anyLong());
        assertThat(actualAuthor).isEqualTo(testAuthor.toDto());

    }

    @Test
    void testCreateAuthor_withEmptyBookIds_thenReturnAuthor() {
        AuthorEntity testAuthor = createTestAuthor();
        testAuthor.setBooks(List.of());

        when(authorRepository.save(any())).thenReturn(testAuthor);

        AuthorDto actualAuthor = authorService.createAuthor(
                new AuthorCreateRequest("John", "Doe", List.of()));

        verify(authorRepository, times(1)).save(any());
        verify(bookRepository, times(0)).findById(anyLong());
        assertThat(actualAuthor).isEqualTo(testAuthor.toDto());

    }

    @Test
    void testCreateAuthor_withInvalidBookId_thenThrowNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> authorService.createAuthor(
                new AuthorCreateRequest("John", "Doe", List.of(1L, 2L))));

        assertThat(exception).hasMessage("Book with id 1 not found");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAuthor_withValidAuthor_thenReturnAuthor() {
        AuthorEntity testAuthor = createTestAuthor();
        AuthorEntity updatedAuthor = createTestAuthor();
        updatedAuthor.setFirstName("Algorithms");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testAuthor.getBooks().get(0)));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(testAuthor.getBooks().get(1)));
        when(authorRepository.save(any())).thenReturn(updatedAuthor);

        AuthorDto actualAuthor = authorService.updateAuthor(
                1L, new AuthorUpdateRequest("Algorithms", "Doe", List.of(1L, 2L)));

        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(2)).findById(anyLong());
        verify(authorRepository, times(1)).save(any());
        assertThat(actualAuthor).isEqualTo(testAuthor.toDto());
    }

    @Test
    void testUpdateAuthor_withNonExistentAuthor_thenThrowNotFoundException() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authorService.updateAuthor(
                        1L, new AuthorUpdateRequest("Algorithms", "Doe", List.of(1L, 2L))
                )
        );

        assertThat(exception).hasMessage(AUTHOR_WITH_ID_NOT_FOUND);
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(0)).findById(anyLong());
        verify(authorRepository, times(0)).save(any());
    }

    @Test
    void testUpdateAuthor_withNonExistentBook_thenThrowNotFoundException() {
        AuthorEntity testAuthor = createTestAuthor();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(testAuthor));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                authorService.updateAuthor(
                        1L, new AuthorUpdateRequest("Algorithms", "Doe", List.of(1L, 2L))
                )
        );

        assertThat(exception).hasMessage("Book with id 1 not found");
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(authorRepository, times(0)).save(any());
    }

    @Test
    void deleteAuthor_whenAuthorExists_shouldDeleteAuthor() {
        AuthorEntity existingAuthor = new AuthorEntity();
        existingAuthor.setId(validAuthorId);

        when(authorRepository.findById(validAuthorId)).thenReturn(Optional.of(existingAuthor));

        assertDoesNotThrow(() -> authorService.deleteAuthor(validAuthorId));

        verify(authorRepository, times(1)).deleteById(validAuthorId);
    }

    @Test
    void deleteAuthor_whenAuthorDoesNotExist_shouldThrowException() {
        when(authorRepository.findById(invalidAuthorId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> authorService.deleteAuthor(invalidAuthorId));

        verify(authorRepository, never()).deleteById(anyLong());
        assertThat(exception).hasMessage("Author with id " + invalidAuthorId + " not found");
    }


    private AuthorEntity createTestAuthor() {
        AuthorEntity testAuthor = new AuthorEntity();
        testAuthor.setId(1L);
        testAuthor.setFirstName("John");
        testAuthor.setLastName("Doe");
        testAuthor.setBooks(createTestBooks());
        return testAuthor;
    }

    private List<BookEntity> createTestBooks() {
        GenreEntity genre = new GenreEntity();
        genre.setId(1L);
        genre.setName("Algorithms Genre");

        BookEntity book1 = new BookEntity();
        book1.setId(1L);
        book1.setName("Book 1");
        book1.setPrice(BigDecimal.valueOf(10.0));
        book1.setGenre(genre);

        BookEntity book2 = new BookEntity();
        book2.setId(2L);
        book2.setName("Book 2");
        book2.setPrice(BigDecimal.valueOf(20.0));
        book2.setGenre(genre);

        return List.of(book2, book2);
    }

}