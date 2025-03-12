package com.libra.bookshoptest.client;

import com.libra.bookshopmodel.dto.book.BookDto;
import com.libra.bookshoptest.client.fallback.BookServiceFallback;
import com.libra.bookshoptest.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "bookshop-book", configuration = FeignConfig.class, fallback = BookServiceFallback.class)
public interface BookFeignClient {

    @GetMapping("/books/{id}")
    BookDto findBookById(@PathVariable("id") Long id);
}
