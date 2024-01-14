package com.url.shorter.features.link.services;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ShortLinkGenerator {
    private ShortLinkGenerator(){
    }
    private static final String symbolsString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int linkSize = 8;
    public String shortLinkGenerator(String st) {

        String protocol;
        String[] words = st.split("//");

        if(words[0].equals("http:")){
            protocol = words[0]+"//";
        } else {
            protocol = "https://";
        }

        String newLink = new Random().ints(linkSize, 0, symbolsString.length())
                .mapToObj(symbolsString::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return protocol+newLink;
    }
}
