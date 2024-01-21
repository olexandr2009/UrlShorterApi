package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class) @MockitoSettings(strictness = Strictness.LENIENT)
public class LinkServiceImplTest {

    @Autowired
    private LinkServiceImpl linkService;

    @MockBean
    private LinkRepository linkRepository;

    @Test
    void testFindAllLinks() {

        // Генерація даних для тесту
        UUID userId = UUID.randomUUID();
        UserDto userDto = new UserDto(userId);

        List<LinkEntity> linkEntities = Arrays.asList(
                new LinkEntity(UUID.randomUUID(), "long1", "short1", new UserEntity(userId), LocalDateTime.now(), LocalDateTime.now().plusDays(7), 0),
                new LinkEntity(UUID.randomUUID(), "long2", "short2", new UserEntity(userId), LocalDateTime.now(), LocalDateTime.now().plusDays(7), 0)
        );

        when(linkRepository.findByUserId(any())).thenReturn(linkEntities);

        // Виклик методу
        List<LinkDto> result = linkService.findAllLinks(userDto);

        // Перевірка
        assertEquals(linkEntities.size(), result.size());

        // Перевірка findByUserId
        verify(linkRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testRedirect_ExistingShortLink_Success() {
        // Генерція даних
        String shortLink = "abc123";
        String longLink = "https://www.example.com";
        LinkEntity linkEntity = new LinkEntity(shortLink, longLink);
        linkEntity.setClicks(0); // Assuming clicks are initially zero

        Mockito.when(linkRepository.findByShortLink(shortLink)).thenReturn(Optional.of(linkEntity));

        // Виклик
        LinkDto result = linkService.redirect(shortLink);

        // Перевірка
        Mockito.verify(linkRepository, Mockito.times(1)).save(linkEntity);
        assertEquals(longLink, result.getOriginUrl());
    }

    @Test
    public void testRedirect_NonExistingShortLink_ExceptionThrown() {
        // Генерація
        String nonExistingShortUrl = "nonexistent";
        Mockito.when(linkRepository.findByShortLink(nonExistingShortUrl)).thenReturn(Optional.empty());

        // Перевірка
        assertThrows(IllegalArgumentException.class, () -> linkService.redirect(nonExistingShortUrl));
    }
}

