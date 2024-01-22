package com.url.shorter.features.link.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShortLinkGeneratorTest {

    @Test
    void generateShouldGenerateDifferentShortLinksForDifferentInput() {
        ShortLinkGenerator shortLinkGenerator = new ShortLinkGenerator();
        String longLink1 = "https://example1.com";
        String longLink2 = "https://example2.com";

        String shortLink1 = shortLinkGenerator.generate(longLink1);
        String shortLink2 = shortLinkGenerator.generate(longLink2);

        assertNotEquals(shortLink1, shortLink2);
    }
}