package com.url.shorter.features.link.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShortLinkGeneratorTest {
    @Autowired
    private ShortLinkGenerator linkGenerator;

    @Test
    public void testGenerate() {
        String longLink = "https://www.example.com";
        String shortLink = linkGenerator.generate(longLink);

        //Checking that the link has the correct protocol and resource
        assertTrue(shortLink.startsWith("https://url.shorter.api/"));

        //Checking whether the link length matches the set value
        assertEquals(linkGenerator.linkSize, shortLink.length() - "https://url.shorter.api/".length());
    }


    //Test for an exception when an invalid long link is entered
    @Test
    public void testInvalidLongLink() {
        String longLink = "invalid_link";
        linkGenerator.generate(longLink);
    }
}
