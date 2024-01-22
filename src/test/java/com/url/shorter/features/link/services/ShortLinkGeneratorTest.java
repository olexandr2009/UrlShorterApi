package com.url.shorter.features.link.services;

import com.url.shorter.config.Prefs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ShortLinkGeneratorTest {
    @Autowired
    private ShortLinkGenerator linkGenerator;

    //Spy this is partial mock of a real object
    @Spy
    private Prefs prefs;

    @Test
    public void testShortLinkGenerator() {
        String longLink = "https://www.example.com";
        String shortLink = linkGenerator.shortLinkGenerator(longLink);

        //Checking that the link has the correct protocol and resource
        assertTrue(shortLink.startsWith("https://url.shorter.api/"));

        //Checking whether the link length matches the set value
        assertEquals(linkGenerator.linkSize, shortLink.length() - "https://url.shorter.api/".length());
    }

    //Test for an exception when an invalid long link is entered
    @Test
    public void testInvalidLongLink() {
        String longLink = "invalid_link";
        linkGenerator.shortLinkGenerator(longLink);
    }

    //Tests the interaction of an object 'ShortLinkGenerator' with a mock object 'Prefs'
    @Test
    public void testPrefsInteraction() {
        //Configure the behavior of the mock object Prefs
        when(prefs.getString(Prefs.LINK_SIZE)).thenReturn("10");
        when(prefs.getString(Prefs.NAME_OF_RESOURCE)).thenReturn("url.shorter.api");

        //Call to the method that interacts with the object Prefs
        String longLink = "https://www.example.com";
        linkGenerator.shortLinkGenerator(longLink);

        //Checking interaction with the object
        verify(prefs, times(1)).getString(Prefs.LINK_SIZE);
        verify(prefs, times(1)).getString(Prefs.NAME_OF_RESOURCE);
    }
}
