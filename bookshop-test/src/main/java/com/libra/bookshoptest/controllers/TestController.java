package com.libra.bookshoptest.controllers;

import com.libra.bookshopmodel.dto.book.BookDto;
import com.libra.bookshopmodel.exceptions.NotFoundException;
import com.libra.bookshoptest.client.BookFeignClient;
import com.libra.bookshoptest.dto.TestApplicationInformation;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final Environment environment;
    private final TestApplicationInformation testApplicationInformation;
    private final BookFeignClient bookFeignClient;

    @Value("${build.version}")
    private String version;

    @GetMapping(path = "/value/version")
    public String getVersionFromValue() {
        return "@Value version:" + version;
    }

    @GetMapping(path = "/environment/version")
    public String getVersionFromEnvironment() {
        return "Environment version:" + environment.getProperty("build.version");
    }

    @Retry(name = "applicationInfo", fallbackMethod = "getAppInfoFallback")
    @GetMapping(path = "/configuration/properties/appinfo")
    public TestApplicationInformation getAppInfo() {
        return testApplicationInformation;
    }

    public TestApplicationInformation getAppInfoFallback(Throwable throwable) {
        TestApplicationInformation fallbackInfo = new TestApplicationInformation();
        fallbackInfo.setName("Fallback Test Application");
        fallbackInfo.setVersion("Fallback Version");
        fallbackInfo.setDescription("Fallback Description");
        fallbackInfo.setEnvironment("Fallback Environment");
        fallbackInfo.setDevelopers(List.of("Fallback Developer"));
        return fallbackInfo;
    }

    @GetMapping(path = "/feign/book/{id}")
    public ResponseEntity<?> getBook(@RequestHeader("X-Request-Id") String requestId, @PathVariable Long id) {
        try {
            BookDto book = bookFeignClient.findBookById(id);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found in BOOKS service");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
