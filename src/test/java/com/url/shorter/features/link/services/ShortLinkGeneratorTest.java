package com.url.shorter.features.link.services;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class ShortLinkGeneratorTest {
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;

    @Test
    public void testShortLinkGenerator() {
        String longLink = "https://www.example.com";
        String shortLink = shortLinkGenerator.shortLinkGenerator(longLink);

        assertTrue(shortLink.startsWith("https://example-resource/"));

        assertEquals(shortLinkGenerator.linkSize, shortLink.length() - "https://example-resource/".length());
    }
}
