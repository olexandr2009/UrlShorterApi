package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LinkService {
    Optional<LinkDto> findByShortLink(String shortLink);

    void deleteByShortLink(String shortLink);
}
