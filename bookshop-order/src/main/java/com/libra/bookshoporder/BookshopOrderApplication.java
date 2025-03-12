package com.libra.bookshoporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.libra.bookshoporder",
		"com.libra.bookshopmodel.entity",
		"com.libra.bookshopdata"
})
@EntityScan("com.libra.bookshopmodel.entity")
@EnableJpaRepositories(basePackages = "com.libra.bookshopdata")
public class BookshopOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshopOrderApplication.class, args);
	}

}
