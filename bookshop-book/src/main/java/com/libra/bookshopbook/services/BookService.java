package com.libra.bookshopbook.services;

import com.libra.bookshopbook.aspect.annotations.LogCreateObject;
import com.libra.bookshopdata.repository.AuthorRepository;
import com.libra.bookshopdata.repository.BookRepository;
import com.libra.bookshopdata.repository.GenreRepository;
import com.libra.bookshopmodel.dto.book.BookCreateRequest;
import com.libra.bookshopmodel.dto.book.BookDto;
import com.libra.bookshopmodel.dto.book.BookUpdateRequest;
import com.libra.bookshopmodel.entity.AuthorEntity;
import com.libra.bookshopmodel.entity.BookEntity;
import com.libra.bookshopmodel.entity.GenreEntity;
import com.libra.bookshopmodel.util.AssertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    public static final String BOOK_WITH_ID_D_NOT_FOUND = "Book with id %d not found";
    public static final String AUTHOR_WITH_ID_D_NOT_FOUND = "Author with id %d not found";
    public static final String GENRE_WITH_ID_D_NOT_FOUND = "Genre with id %d not found";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final StreamBridge streamBridge;

    @Transactional(readOnly = true)
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream().map(BookEntity::toDto).toList();
    }

    @Transactional(readOnly = true)
    public BookDto findBookById(Long id) {
        BookEntity book = AssertUtil.notNull(bookRepository.findById(id), String.format(BOOK_WITH_ID_D_NOT_FOUND, id));
        return book.toDto();
    }

    @Transactional
    @LogCreateObject
    public BookDto createBook(BookCreateRequest bookCreateRequest) {
        BookEntity book = new BookEntity();
        processBook(book, bookCreateRequest.getName(), bookCreateRequest.getPrice(), bookCreateRequest.getAuthorId(), bookCreateRequest.getGenreId());
        BookDto bookDto = bookRepository.save(book).toDto();

        boolean result = streamBridge.send("emailBooksmsBook-out-0", bookDto);
        log.info("Is email and sms event triggered?: {}", result);

        return bookDto;
    }

    @Transactional
    public BookDto updateBook(Long id, BookUpdateRequest bookUpdateRequest) {
        BookEntity book = AssertUtil.notNull(bookRepository.findById(id), String.format(BOOK_WITH_ID_D_NOT_FOUND, id));
        processBook(book, bookUpdateRequest.getName(), bookUpdateRequest.getPrice(), bookUpdateRequest.getAuthorId(), bookUpdateRequest.getGenreId());
        return bookRepository.save(book).toDto();
    }

    @Transactional
    public void deleteBook(Long id) {
        BookEntity book = AssertUtil.notNull(bookRepository.findById(id), String.format(BOOK_WITH_ID_D_NOT_FOUND, id));
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> findBooksByAuthorId(Long authorId) {
        AuthorEntity author = AssertUtil.notNull(authorRepository.findById(authorId),
                String.format(AUTHOR_WITH_ID_D_NOT_FOUND, authorId));
        return bookRepository.findByAuthorId(author.getId()).stream().map(BookEntity::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> findBooksByGenreId(Long genreId) {
        GenreEntity genre = AssertUtil.notNull(genreRepository.findById(genreId),
                String.format(GENRE_WITH_ID_D_NOT_FOUND, genreId));
        return bookRepository.findByGenreId(genre.getId()).stream().map(BookEntity::toDto).toList();
    }

    /**
     * Method triggered by email and sms event, when book is created. Methode uses for testing.
     *
     * @param id - book id
     */
    public void getProcessedMessageResult(Long id) {
        log.info("Email and Sms event triggered for book with id: {}", id);
    }

    private void processBook(BookEntity book, String bookUpdateRequest, BigDecimal bookUpdateRequest1, Long bookUpdateRequest2, Long bookUpdateRequest3) {
        book.setName(bookUpdateRequest);
        book.setPrice(bookUpdateRequest1);

        AuthorEntity author = AssertUtil.notNull(authorRepository.findById(bookUpdateRequest2),
                String.format(AUTHOR_WITH_ID_D_NOT_FOUND, bookUpdateRequest2));
        book.setAuthor(author);

        GenreEntity genre = AssertUtil.notNull(genreRepository.findById(bookUpdateRequest3),
                String.format(GENRE_WITH_ID_D_NOT_FOUND, bookUpdateRequest3));
        book.setGenre(genre);
    }
}
