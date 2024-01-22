package com.url.shorter.features.link.services;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.user.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LinkService {
    Optional<LinkDto> findByShortLink(String shortLink);

    void deleteByShortLink(String shortLink);
    LinkDto createByLongLink(LinkDto linkDto);
    LinkDto updateByLongLink(LinkDto linkDto);
    List<LinkDto> findAll();
    Optional<LinkDto> findByLongLink(String longLink);
    void deleteByLongLink(String longLink);
    List<LinkDto> findActiveLinks();
    List<LinkDto> findActiveLinks(UserDto userDto);
    Optional<LinkDto> findByShortLink(String shortLink);
    void deleteByShortLink(String shortLink);
    List<LinkDto> findAllLinks(UserDto userDto);
    LinkDto redirect(String shortUrl);
}
