package com.url.shorter.features.link.services;

import com.url.shorter.features.link.entities.LinkEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LinkService {
    Optional<LinkEntity> findByShortLink(String shortLink);

    void deleteByShortLink(String shortLink);
}
