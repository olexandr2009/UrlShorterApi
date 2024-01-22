package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.user.dtos.UserDto;

import java.util.List;
import java.util.Optional;


public interface LinkService {
    LinkDto createByLongLink(LinkDto linkDto);
    LinkDto updateByLongLink(LinkDto linkDto);
    List<LinkDto> findAll();
    Optional<LinkDto> findByLongLink(String longLink);
    void deleteByLongLink(String longLink);
    List<LinkDto> findActiveLinks();
    Optional<LinkDto> findByShortLink(String shortLink);
    void deleteByShortLink(String shortLink);
    List<LinkDto> findAllLinks(UserDto userDto);
    boolean existsByShortLink(String shortLink);
}