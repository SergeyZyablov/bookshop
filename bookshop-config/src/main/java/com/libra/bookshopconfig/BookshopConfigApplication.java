package com.libra.bookshopconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BookshopConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopConfigApplication.class, args);
    }

}
