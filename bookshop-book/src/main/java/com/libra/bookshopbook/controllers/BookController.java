package com.libra.bookshopbook.controllers;

import com.libra.bookshopbook.services.BookService;
import com.libra.bookshopmodel.dto.book.BookCreateRequest;
import com.libra.bookshopmodel.dto.book.BookDto;
import com.libra.bookshopmodel.dto.book.BookUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto findBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id);
    }

    @GetMapping(("/author/{authorId}"))
    public List<BookDto> findBooksByAuthorId(@PathVariable("authorId") Long authorId) {
        return bookService.findBooksByAuthorId(authorId);
    }

    @GetMapping(("/genre/{genreId}"))
    public List<BookDto> findBooksByGenreId(@PathVariable("genreId") Long genreId) {
        return bookService.findBooksByGenreId(genreId);
    }

    @PostMapping
    public BookDto createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return bookService.createBook(bookCreateRequest);
    }

    @PostMapping("/update/{id}")
    public BookDto updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        return bookService.updateBook(id, bookUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
