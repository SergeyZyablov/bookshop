package com.libra.bookshopauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.libra.bookshopmodel.entity")
@EnableJpaRepositories(basePackages = "com.libra.bookshopdata")
public class BookshopUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopUserApplication.class, args);
    }

}
