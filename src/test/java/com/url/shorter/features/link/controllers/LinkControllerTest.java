package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class LinkControllerTest {

    @Mock
    private LinkService linkService;

    @InjectMocks
    private LinkController linkController;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testFindActiveLinks() {
        List<LinkDto> activeLinks = Arrays.asList(
                new LinkDto(), new LinkDto(), new LinkDto()
        );

        when(linkService.findActiveLinks()).thenReturn(activeLinks);

        ResponseEntity<List<LinkDto>> response = linkController.findActiveLinks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activeLinks, response.getBody());
    }

    @Test
    void testDeleteByLongLink() {
        String longLink = "https://google.com";
        ResponseEntity<Void> response = linkController.deleteByLongLink(longLink);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(linkService, times(1)).deleteByLongLink(longLink);
    }
}