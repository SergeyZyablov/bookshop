package com.libra.bookshopbook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libra.bookshopbook.aspect.annotations.LogCreateObject;
import com.libra.bookshopbook.aspect.annotations.LogUpdateObject;
import com.libra.bookshopdata.repository.AuthorRepository;
import com.libra.bookshopdata.repository.BookRepository;
import com.libra.bookshopmodel.dto.author.AuthorCreateRequest;
import com.libra.bookshopmodel.dto.author.AuthorDto;
import com.libra.bookshopmodel.dto.author.AuthorUpdateRequest;
import com.libra.bookshopmodel.entity.AuthorEntity;
import com.libra.bookshopmodel.entity.BookEntity;
import com.libra.bookshopmodel.util.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorService {

    public static final String AUTHOR_WITH_ID_D_NOT_FOUND = "Author with id %d not found";
    public static final String BOOK_WITH_ID_NOT_FOUND = "Book with id %d not found";

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final JedisPool jedisPool;
    private final ObjectMapper mapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> findAllAuthors() {
        return authorRepository.findAll().stream().map(AuthorEntity::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "author", key = "#id")
    public AuthorDto findAuthorById(Long id) {
        AuthorEntity author = AssertUtil.notNull(authorRepository.findById(id), String.format(AUTHOR_WITH_ID_D_NOT_FOUND, id));
        return author.toDto();
    }

    public AuthorDto getCashedAuthorById(Long id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "author:" + id;
            String authorJson = jedis.get(key);
            if (authorJson != null) {
                return mapper.readValue(authorJson, AuthorDto.class);
            }
            AuthorDto authorDto = findAuthorById(id);
            jedis.setex(key, 120, mapper.writeValueAsString(authorDto));
            return authorDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @LogCreateObject
    @Transactional
    public AuthorDto createAuthor(AuthorCreateRequest authorCreateRequest) {
        AuthorEntity author = new AuthorEntity();
        author.setFirstName(authorCreateRequest.getFirstName());
        author.setLastName(authorCreateRequest.getLastName());

        List<BookEntity> books = getBooks(authorCreateRequest.getBookIds());
        author.setBooks(books);

        return authorRepository.save(author).toDto();
    }

    @LogUpdateObject
    @Transactional
    public AuthorDto updateAuthor(Long id, AuthorUpdateRequest authorUpdateRequest) {
        AuthorEntity author = AssertUtil.notNull(authorRepository.findById(id), String.format(AUTHOR_WITH_ID_D_NOT_FOUND, id));
        author.setFirstName(authorUpdateRequest.getFirstName());
        author.setLastName(authorUpdateRequest.getLastName());

        List<BookEntity> books = getBooks(authorUpdateRequest.getBookIds());
        author.setBooks(books);

        return authorRepository.save(author).toDto();
    }

    @Transactional
    public void deleteAuthor(Long id) {
        AssertUtil.notNull(authorRepository.findById(id), String.format(AUTHOR_WITH_ID_D_NOT_FOUND, id));
        authorRepository.deleteById(id);
    }

    private List<BookEntity> getBooks(List<Long> bookIds) {
        if (bookIds == null || bookIds.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> bookIdsSet = new HashSet<>(bookIds);
        return bookIdsSet.stream().map(bookId ->
                        AssertUtil.notNull(bookRepository.findById(bookId),
                                String.format(BOOK_WITH_ID_NOT_FOUND, bookId)))
                .toList();
    }
}
