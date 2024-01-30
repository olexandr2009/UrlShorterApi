package com.url.shorter.features.link.services;

import com.url.shorter.config.Prefs;
import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import com.url.shorter.features.user.entities.UserEntity;
import com.url.shorter.features.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"links"})
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Prefs prefs;

    @Transactional
    @Override
    public List<LinkDto> findAll() {
        List<LinkEntity> allLinks = linkRepository.findAll();
        return allLinks.stream()
                .map(LinkDto::fromEntity)
                .collect(Collectors.toList());
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    @Transactional
    @Override
    @CachePut("#linkDto.id")
    public LinkDto createByLongLink(LinkDto linkDto) {
        if (linkDto == null || linkDto.getLongLink() == null || !isValidURL(linkDto.getLongLink())) {
            throw new IllegalArgumentException("Invalid input data for creating a link.");
        }

        String shortLink = shortLinkGenerator.generate();

        LocalDateTime expirationDate = linkDto.getExpirationDate() == null
                ? LocalDateTime.now().plusDays(7) : linkDto.getExpirationDate();

        LinkEntity entity = LinkEntity.builder()
                .longLink(linkDto.getLongLink())
                .shortLink(shortLink)
                .creationDate(linkDto.getCreationDate())
                .expirationDate(expirationDate)
                .clicks(linkDto.getOpenCount())
                .owner(userRepository.findByUsername(linkDto.getOwnerName()).orElseThrow())
                .build();

        entity = linkRepository.saveAndFlush(entity);

        return LinkDto.fromEntity(entity);
    }

    @Transactional
    @Override
    @CachePut("#linkDto.id")
    public LinkDto updateByLongLink(LinkDto linkDto) {
        Optional<LinkEntity> existingLink = linkRepository.findByLongLink(linkDto.getLongLink());
        if (existingLink.isEmpty()) {
            throw new IllegalArgumentException("Link with the provided long link does not exist.");
        }

        LinkEntity existingEntity = existingLink.get();
        existingEntity.setShortLink(linkDto.getShortLink());
        existingEntity.setExpirationDate(linkDto.getExpirationDate());
        existingEntity.setClicks(linkDto.getOpenCount());

        LinkEntity updatedEntity = linkRepository.save(existingEntity);
        return LinkDto.fromEntity(updatedEntity);
    }

    @Override
    @Cacheable(key = "#longLink")
    public Optional<LinkDto> findByLongLink(String longLink) {
        return linkRepository.findByLongLink(longLink)
                .map(LinkDto::fromEntity);
    }

    @Transactional
    @Override
    @CacheEvict(key = "#longLink")
    public void deleteByLongLink(String longLink) {
        Optional<LinkEntity> linkToDelete = linkRepository.findByLongLink(longLink);
        if (linkToDelete.isEmpty()) {
            throw new IllegalArgumentException("Link with the provided long link does not exist.");
        }

        linkRepository.delete(linkToDelete.get());
    }

    @Override
    public List<LinkDto> findActiveLinks() {

        return findAll()
                .stream()
                .filter(link -> link.getExpirationDate().isAfter(LocalDateTime.now()))
                .toList();
    }

    @Override
    public List<LinkDto> findActiveLinks(String username) {
        return findAllLinks(username)
                .stream()
                .filter(link -> link.getExpirationDate().isAfter(LocalDateTime.now()))
                .toList();
    }


    @Cacheable(key = "#shortLink")
    public Optional<LinkDto> findByShortLink(String shortLink) {
        try {
            return linkRepository.findByShortLinkEndsWith(shortLink)
                    .map(LinkDto::fromEntity);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    @CacheEvict(key = "#shortLink")
    public void deleteByShortLink(String shortLink) {
        // Get link entity from DB. Throw exception if entity is missing
        LinkEntity linkEntity = linkRepository.findByShortLinkEndsWith(shortLink)
                .orElseThrow(() -> new IllegalArgumentException("Link with the provided short link does not exist."));
        // Delete Entity from DB
        linkRepository.delete(linkEntity);
    }

    @Override
    public List<LinkDto> findAllLinks(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        return userEntity.getLinks().stream()
                .map(LinkDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(key = "#shortLink")
    public void incrementUseCount(String shortLink){
        linkRepository.incrementOpenCount(shortLink);
    }
}
