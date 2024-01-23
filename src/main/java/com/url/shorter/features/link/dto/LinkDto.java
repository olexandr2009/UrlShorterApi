package com.url.shorter.features.link.dto;

import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.user.entities.UserEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Link DTO (data transfer object)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    UUID id;
    String shortUrl;
    String originUrl;
    LocalDateTime creationDate;
    LocalDateTime expirationDate;
    int openCount;
    UUID userId;

    public LinkEntity toEntity() {
        return LinkEntity.builder()
                .id(id)
                .shortLink(shortUrl)
                .longLink(originUrl)
                .creationDate(creationDate)
                .expirationDate(expirationDate)
                .clicks(openCount)
                .user(UserEntity.builder().id(userId).build())
                .build();
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
    public String getLongLink() {
        return originUrl;
    }
}
