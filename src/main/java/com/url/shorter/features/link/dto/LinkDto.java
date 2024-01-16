package com.url.shorter.features.link.dto;

import com.url.shorter.features.link.entities.LinkEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Link DTO (data transfer object)
 */
@Data
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    UUID id;
    String shortUrl;
    String originUrl;
    LocalDateTime creationDate;
    LocalDateTime expirationDate;
    Integer openCount;
    UUID userId;

    public LinkEntity toEntity() {
        return new LinkEntity(id, shortUrl, originUrl, creationDate, expirationDate, openCount, userId);
    }

    public static LinkDto fromEntity(LinkEntity linkEntity) {
        return LinkDto.builder()
                .id(linkEntity.getId())
                .shortUrl(linkEntity.getShortLink())
                .originUrl(linkEntity.getLongLink())
                .creationDate(linkEntity.getCreationDate())
                .openCount(linkEntity.getClicks())
                .expirationDate(linkEntity.getExpirationDate())
                .userId(linkEntity.getUser() != null ? linkEntity.getUser().getId() : null)
                .build();
    }
}
