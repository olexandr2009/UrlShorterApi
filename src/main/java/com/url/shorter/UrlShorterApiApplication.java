package com.url.shorter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UrlShorterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShorterApiApplication.class, args);
    }
}
