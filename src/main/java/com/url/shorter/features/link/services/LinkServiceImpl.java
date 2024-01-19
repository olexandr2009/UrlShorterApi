package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import com.url.shorter.features.user.entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
public class LinkServiceImpl implements LinkService{

    private final LinkRepository linkRepository;

    @Autowired
    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    @Override
    public LinkDto createByLongLink(LinkDto linkDto) {
        if (linkDto == null || linkDto.getOriginUrl() == null) {
            throw new IllegalArgumentException("Invalid input data for creating a link.");
        }

        //additional logic for generating a new link

        LinkEntity linkEntity = linkRepository.save(linkDto.toEntity());
        return LinkDto.fromEntity(linkEntity);
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
    public List<LinkDto> findAllLinks(UserEntity userEntity) {
        UUID userId = userEntity.getId();
        List<LinkEntity> linkEntities = linkRepository.findByUserId(userId);
        return linkEntities.stream()
                .map(LinkDto::fromEntity)
                .collect(Collectors.toList());
    }
}
