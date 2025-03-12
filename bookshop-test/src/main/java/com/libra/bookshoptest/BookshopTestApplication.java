package com.libra.bookshoptest;

import com.libra.bookshoptest.dto.TestApplicationInformation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.libra.bookshoptest",
        "com.libra.bookshopmodel.entity",
        "com.libra.bookshopdata"
})
@EntityScan("com.libra.bookshopmodel.entity")
@EnableJpaRepositories(basePackages = "com.libra.bookshopdata")
@EnableConfigurationProperties(TestApplicationInformation.class)
@EnableFeignClients
public class BookshopTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopTestApplication.class, args);
    }

}
