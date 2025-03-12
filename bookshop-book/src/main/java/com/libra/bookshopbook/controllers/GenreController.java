package com.libra.bookshopbook.controllers;

import com.libra.bookshopbook.services.GenreService;
import com.libra.bookshopmodel.dto.genre.GenreCreateRequest;
import com.libra.bookshopmodel.dto.genre.GenreDto;
import com.libra.bookshopmodel.dto.genre.GenreUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> findAllGenres() {
        return genreService.findAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDto findById(@PathVariable("id") Long id) {
        return genreService.findById(id);
    }

    @PostMapping
    public GenreDto save(@Valid @RequestBody GenreCreateRequest genreCreateRequest) {
        return genreService.save(genreCreateRequest);
    }

    @PutMapping("/{id}")
    public GenreDto update(@PathVariable("id") Long id, @Valid @RequestBody GenreUpdateRequest genreUpdateRequest) {
        return genreService.update(id, genreUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        genreService.delete(id);
    }
}
