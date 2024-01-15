package com.url.shorter.features.link.services;

import com.url.shorter.features.link.entities.LinkEntity;

import java.util.Optional;

public interface LinkService {
    Optional<LinkEntity> findByLongLink(String longLink);

    void deleteByLongLink(String longLink);
}
