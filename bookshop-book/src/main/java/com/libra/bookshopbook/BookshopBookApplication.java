package com.libra.bookshopbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.libra.bookshopbook",
        "com.libra.bookshopmodel.entity",
        "com.libra.bookshopdata",
})
@EntityScan("com.libra.bookshopmodel.entity")
@EnableJpaRepositories(basePackages = "com.libra.bookshopdata")
public class BookshopBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopBookApplication.class, args);
    }

}
