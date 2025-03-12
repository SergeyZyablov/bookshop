package com.libra.bookshoptest.client.fallback;

import com.libra.bookshopmodel.dto.book.BookDto;
import com.libra.bookshoptest.client.BookFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookServiceFallback implements BookFeignClient {

    public static final String NAME = "Unknown";

    @Override
    public BookDto findBookById(Long id) {
        BookDto bookDto = new BookDto();
        bookDto.setId(null);
        bookDto.setName(NAME);
        bookDto.setGenreName(NAME);
        bookDto.setAuthorName(NAME);
        return bookDto;
    }
}
