package com.libra.bookshopbook.controllers;

import com.libra.bookshopbook.services.AuthorService;
import com.libra.bookshopmodel.dto.author.AuthorCreateRequest;
import com.libra.bookshopmodel.dto.author.AuthorDto;
import com.libra.bookshopmodel.dto.author.AuthorUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDto> findAllAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto findById(@PathVariable("id") Long id) {
        return authorService.findAuthorById(id);
    }

    @GetMapping("/cashed/{id}")
    public AuthorDto getCashedAuthorById(@PathVariable("id") Long id) {
        return authorService.getCashedAuthorById(id);
    }

    @PostMapping
    public AuthorDto createAuthor(@Valid @RequestBody AuthorCreateRequest authorCreateRequest) {
        return authorService.createAuthor(authorCreateRequest);
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody AuthorUpdateRequest authorUpdateRequest) {
        return authorService.updateAuthor(id, authorUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }
}
