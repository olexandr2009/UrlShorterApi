package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;

import java.util.Optional;

public interface LinkService {
    LinkDto createByLongId(LinkDto linkDto);
    LinkDto updateByLongLink(LinkDto linkDto);
    Optional<LinkDto> findByLongLink(String longLink);
    void deleteByLongLink(String longLink);
}
