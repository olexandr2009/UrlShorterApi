package com.url.shorter;

import com.url.shorter.features.link.services.ShortLinkGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShorterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShorterApiApplication.class, args);
    }
}
