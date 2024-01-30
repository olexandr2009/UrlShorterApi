package com.url.shorter.features.link.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShortLinkGeneratorTest {
    @Autowired
    private ShortLinkGenerator linkGenerator;

    @Test
    public void testGenerate() {
        String shortLink = linkGenerator.generate();

        //Checking that the link has the correct protocol and resource
        assertTrue(shortLink.startsWith("http"));
    }
}
