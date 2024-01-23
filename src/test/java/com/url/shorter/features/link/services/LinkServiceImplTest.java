package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import com.url.shorter.features.user.entities.UserEntity;
import com.url.shorter.features.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;
    @Mock
    private ShortLinkGenerator shortLinkGenerator;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    void testCreateByLongLink() {
        when(linkRepository.saveAndFlush(any())).thenReturn(createTestLinkEntity());
        when(shortLinkGenerator.generate(any())).thenReturn("http://example.com");
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));

        LinkDto testLinkDto = createTestLinkDto();

        LinkDto savedLinkDto = linkService.createByLongLink(testLinkDto);

        assertEquals(testLinkDto.getOriginUrl(), savedLinkDto.getOriginUrl());
        assertEquals(testLinkDto.getShortUrl(), savedLinkDto.getShortUrl());
    }

    @Test
    void testFindAll() {
        when(linkRepository.findAll()).thenReturn(Arrays.asList(createTestLinkEntity()));

        List<LinkDto> allLinks = linkService.findAll();

        assertEquals(1, allLinks.size());

    }

    @Test
    public void testRedirect_ExistingShortLink_Success() {
        // Генерація
        String shortLink = "abc123";
        String longLink = "https://www.example.com";
        LinkEntity linkEntity = new LinkEntity(shortLink, longLink);
        linkEntity.setClicks(0);

        when(linkRepository.findByShortLink(shortLink)).thenReturn(Optional.of(linkEntity));

        // Виклик
        LinkDto linkDto = linkService.redirect(shortLink);

        // Перевірка
        assertNotNull(linkDto);
        assertEquals(longLink, linkDto.getLongLink());

        // Перевірка save
        verify(linkRepository, times(1)).save(linkEntity);
    }

    @Test
    public void testRedirect_NonExistingShortLink_ExceptionThrown() {
        // Генерація
        String nonExistingShortUrl = "nonexistent";
        when(linkRepository.findByShortLink(nonExistingShortUrl)).thenReturn(Optional.empty());

        // Перевірка
        assertThrows(IllegalArgumentException.class, () -> linkService.redirect(nonExistingShortUrl));

        // Перевірка, що save не буде без shortLink
        verify(linkRepository, never()).save(any());
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
    }
}
