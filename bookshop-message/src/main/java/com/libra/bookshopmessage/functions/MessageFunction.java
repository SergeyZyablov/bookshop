package com.libra.bookshopmessage.functions;

import com.libra.bookshopmodel.dto.book.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class MessageFunction {

    @Bean
    public Function<BookDto, BookDto> emailBook() {
        return bookDto -> {
            log.info("Sending email with book details: {}", bookDto);
            return bookDto;
        };
    }

    @Bean
    public Function<BookDto, Long> smsBook() {
        return bookDto -> {
            log.info("Sending sms with book details: {}", bookDto);
            return bookDto.getId();
        };
    }
}
