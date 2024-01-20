package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;

import java.util.Optional;
import java.util.List;

public interface LinkService {
    LinkDto createByLongLink(LinkDto linkDto);
    LinkDto updateByLongLink(LinkDto linkDto);
    List<LinkDto> findAll();
    Optional<LinkDto> findByLongLink(String longLink);
    void deleteByLongLink(String longLink);
}
