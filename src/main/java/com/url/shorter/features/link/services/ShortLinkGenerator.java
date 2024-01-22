package com.url.shorter.features.link.services;

import com.url.shorter.config.Prefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ShortLinkGenerator {
    @Autowired
    private static final String symbolsString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final int linkSize = 8;
    public final String resource = "https://url.shorter.api/";

    public String generate(String longLink) {

        String newLink = new Random().ints(linkSize, 0, symbolsString.length())
                .mapToObj(symbolsString::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return resource+newLink;
    }
}