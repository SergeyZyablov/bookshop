package com.libra.bookshoptest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("test")
@Getter
@Setter
public class TestApplicationInformation {
    private String name;
    private String version;
    private String description;
    private String environment;
    private List<String> developers;
}
