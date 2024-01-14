package com.url.shorter.features.link.services;

import com.url.shorter.Prefs;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ShortLinkGenerator {
    private static final String symbolsString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int linkSize = 8;
    public static final String resource = new Prefs().getString(Prefs.NAME_OF_RESOURCE);
    public String shortLinkGenerator(String longLink) {

        String[] protocol = longLink.split("//");

        String newLink = new Random().ints(linkSize, 0, symbolsString.length())
                .mapToObj(symbolsString::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return protocol[0]+resource+newLink;
    }
}
