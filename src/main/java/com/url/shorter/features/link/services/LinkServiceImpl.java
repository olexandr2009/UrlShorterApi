package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;
    @Autowired
    private UserRepository userRepository;

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
    public LinkDto createByLongLink(LinkDto linkDto) {
        if (linkDto == null || linkDto.getOriginUrl() == null || !isValidURL(linkDto.getOriginUrl())) {
            throw new IllegalArgumentException("Invalid input data for creating a link.");
        }

        String shortLink = shortLinkGenerator.generate(linkDto.getOriginUrl());

        LinkEntity entity = linkDto.toEntity();

        entity.setShortLink(shortLink);
        entity.setUser(userRepository.findById(linkDto.getUserId()).orElseThrow());
        entity = linkRepository.saveAndFlush(entity);

        return LinkDto.fromEntity(entity);
    }

    @Transactional
    @Override
    public LinkDto updateByLongLink(LinkDto linkDto) {
        Optional<LinkEntity> existingLink = linkRepository.findByLongLink(linkDto.getOriginUrl());
        if (existingLink.isEmpty()) {
            throw new IllegalArgumentException("Link with the provided long link does not exist.");
        }

        LinkEntity existingEntity = existingLink.get();
        existingEntity.setShortLink(linkDto.getShortUrl());
        existingEntity.setExpirationDate(linkDto.getExpirationDate());
        existingEntity.setClicks(linkDto.getOpenCount());

        LinkEntity updatedEntity = linkRepository.save(existingEntity);
        return LinkDto.fromEntity(updatedEntity);
    }

    @Override
    public Optional<LinkDto> findByLongLink(String longLink) {
        return linkRepository.findByLongLink(longLink)
                .map(LinkDto::fromEntity);
    }

    @Transactional
    @Override
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
    public List<LinkDto> findActiveLinks(UserDto userDto) {

        return findAllLinks(userDto)
                .stream()
                .filter(link -> link.getExpirationDate().isAfter(LocalDateTime.now()))
                .toList();
    }


    public Optional<LinkDto> findByShortLink(String shortLink) {
        return linkRepository.findByShortLink(shortLink)
                .map(LinkDto::fromEntity);
    }

    @Transactional
    @Override
    public void deleteByShortLink(String shortLink) {
        // Get link entity from DB. Throw exception if entity is missing
        LinkEntity linkEntity = linkRepository.findByShortLink(shortLink)
                .orElseThrow(() -> new IllegalArgumentException("Link with the provided short link does not exist."));
        // Delete Entity from DB
        linkRepository.delete(linkEntity);
    }

    @Override
    public List<LinkDto> findAllLinks(UserDto userDto) {
        UUID userId = userDto.getId();
        List<LinkEntity> linkEntities = linkRepository.findByUserId(userId);
        return linkEntities.stream()
                .map(LinkDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public LinkDto redirect(String shortUrl) {
        Optional<LinkEntity> linkEntityOptional = linkRepository.findByShortLink(shortUrl);

        if (linkEntityOptional.isEmpty()) {
            throw new IllegalArgumentException("Can`t find current short link in DB");
        }
        LinkEntity linkEntity = linkEntityOptional.get();

        linkEntity.setClicks(linkEntity.getClicks() + 1);
        linkRepository.save(linkEntity);

        return LinkDto.fromEntity(linkEntity);
    }

    @Override
    public boolean existsByShortLink(String shortLink) {
        return linkRepository.existsByShortLink(shortLink);
    }
}
