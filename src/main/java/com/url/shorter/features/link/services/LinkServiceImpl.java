package com.url.shorter.features.link.services;

import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.repositories.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LinkServiceImpl {
    private final LinkRepository linkRepository;

    public LinkEntity createByLongLink(LinkEntity linkEntity) {
        if (linkEntity == null || linkEntity.getLongLink() == null) {
            throw new IllegalArgumentException("Invalid input data for creating a link.");
        }

        return linkRepository.save(linkEntity);
    }

    public LinkEntity updateByLongLink(LinkEntity linkEntity) {
        Optional<LinkEntity> existingLink = linkRepository.findByLongLink(linkEntity.getLongLink());
        if (existingLink.isEmpty()) {
            throw new IllegalArgumentException("Link with the provided long link does not exist.");
        }

        return linkRepository.save(linkEntity);
    }

    public Optional<LinkEntity> findByLongLink(String longLink) {
        return linkRepository.findByLongLink(longLink);
    }

    public void deleteByLongLink(String longLink) {
        Optional<LinkEntity> linkToDelete = linkRepository.findByLongLink(longLink);
        if (linkToDelete.isEmpty()) {
            throw new IllegalArgumentException("Link with the provided long link does not exist.");
        }

        linkRepository.delete(linkToDelete.get());
    }
}
