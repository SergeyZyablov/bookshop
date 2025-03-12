package com.libra.bookshopbook.services;

import com.libra.bookshopbook.aspect.annotations.LogCreateObject;
import com.libra.bookshopbook.aspect.annotations.LogUpdateObject;
import com.libra.bookshopdata.repository.GenreRepository;
import com.libra.bookshopmodel.dto.genre.GenreCreateRequest;
import com.libra.bookshopmodel.dto.genre.GenreDto;
import com.libra.bookshopmodel.dto.genre.GenreUpdateRequest;
import com.libra.bookshopmodel.entity.GenreEntity;
import com.libra.bookshopmodel.util.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final String GENRE_WITH_ID_D_NOT_FOUND = "Genre with id %d not found";

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public List<GenreDto> findAllGenres() {
        return genreRepository.findAll().stream().map(GenreEntity::toDto).toList();
    }

    @Transactional(readOnly = true)
    public GenreDto findById(Long id) {
        GenreEntity genre = AssertUtil.notNull(genreRepository.findById(id), String.format(GENRE_WITH_ID_D_NOT_FOUND, id));

        return genre.toDto();
    }

    @LogCreateObject
    @Transactional
    public GenreDto save(GenreCreateRequest genreUpdateRequest) {
        GenreEntity genre = new GenreEntity();
        genre.setName(genreUpdateRequest.getName());
        return genreRepository.save(genre).toDto();
    }

    @LogUpdateObject
    @Transactional
    public GenreDto update(Long id, GenreUpdateRequest genreUpdateRequest) {
        GenreEntity genre = AssertUtil.notNull(genreRepository.findById(id), String.format(GENRE_WITH_ID_D_NOT_FOUND, id));
        genre.setName(genreUpdateRequest.getName());
        return genreRepository.save(genre).toDto();
    }

    @Transactional
    public void delete(Long id) {
        AssertUtil.notNull(genreRepository.findById(id), String.format(GENRE_WITH_ID_D_NOT_FOUND, id));
        genreRepository.deleteById(id);
    }
}
