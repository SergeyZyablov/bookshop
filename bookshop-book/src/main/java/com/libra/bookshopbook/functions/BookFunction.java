package com.libra.bookshopbook.functions;

import com.libra.bookshopbook.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class BookFunction {

    @Bean
    public Consumer<Long> responseMessage(BookService bookService) {
        return id -> {
            log.info("Book with id {} has been processed", id);
            bookService.getProcessedMessageResult(id);
        };
    }
}
