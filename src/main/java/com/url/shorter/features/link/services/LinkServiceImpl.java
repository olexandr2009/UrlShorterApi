package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import com.url.shorter.features.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LinkServiceImpl implements LinkService{
    private final LinkRepository linkRepository;
    private final ShortLinkGenerator shortLinkGenerator;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public List<LinkDto> findAll() {
        List<LinkEntity> allLinks = linkRepository.findAll();
        return allLinks.stream()
                .map(LinkDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public LinkDto createByLongLink(LinkDto linkDto) {
        if (linkDto == null || linkDto.getOriginUrl() == null) {
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
}
