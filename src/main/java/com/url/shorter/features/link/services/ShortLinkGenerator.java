package com.url.shorter.features.link.services;

import com.url.shorter.config.Prefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ShortLinkGenerator {
    @Autowired
    private final Prefs prefs = new Prefs();
    private static final String symbolsString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final int linkSize = (int) Double.parseDouble(prefs.getString(Prefs.LINK_SIZE));
    public final String resource = prefs.getString(Prefs.NAME_OF_RESOURCE);

    public String generate() {
        String newLink = new Random().ints(linkSize, 0, symbolsString.length())
                .mapToObj(symbolsString::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return "http://"+resource+"/"+newLink;
    }
}