package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    void testCreateByLongLink() {
        Mockito.when(linkRepository.save(any())).thenReturn(createTestLinkEntity());

        LinkDto testLinkDto = createTestLinkDto();

        LinkDto savedLinkDto = linkService.createByLongLink(testLinkDto);

        assertEquals(testLinkDto.getOriginUrl(), savedLinkDto.getOriginUrl());
        assertEquals(testLinkDto.getShortUrl(), savedLinkDto.getShortUrl());
    }

    @Test
    void testFindAll() {
        Mockito.when(linkRepository.findAll()).thenReturn(Arrays.asList(createTestLinkEntity()));

        List<LinkDto> allLinks = linkService.findAll();

        assertEquals(1, allLinks.size());

    }
    private LinkEntity createTestLinkEntity() {
        return LinkEntity.builder()
                .id(UUID.randomUUID())
                .longLink("http://example.com")
                .shortLink("abc123")
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .clicks(0)
                .build();
    }

    private LinkDto createTestLinkDto() {
        return LinkDto.builder()
                .originUrl("http://example.com")
                .shortUrl("abc123")
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .openCount(0)
                .userId(UUID.randomUUID())
                .build();
    }}
