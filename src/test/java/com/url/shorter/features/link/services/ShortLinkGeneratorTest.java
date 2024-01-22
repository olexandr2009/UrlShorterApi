package com.url.shorter.features.link.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShortLinkGeneratorTest {

    @Test
    public void testGenerate() {
        ShortLinkGenerator linkGenerator = new ShortLinkGenerator();


        String generatedLink = linkGenerator.generate("https://www.example.com");
        assertTrue(generatedLink.startsWith(linkGenerator.resource));

        assertEquals(linkGenerator.linkSize + linkGenerator.resource.length(), generatedLink.length());

        String link1 = linkGenerator.generate("https://www.example.com");
        String link2 = linkGenerator.generate("https://www.anotherexample.com");
        assertNotEquals(link1, link2);
    }
}