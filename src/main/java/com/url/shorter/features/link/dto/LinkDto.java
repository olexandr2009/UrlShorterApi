package com.url.shorter.features.link.dto;

import com.url.shorter.features.link.entities.LinkEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Link DTO (data transfer object)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    String shortLink;
    String longLink;
    LocalDateTime creationDate;
    LocalDateTime expirationDate;
    int openCount;
    String username;

    public static LinkDto fromEntity(LinkEntity linkEntity) {
        return LinkDto.builder()
                .shortLink(linkEntity.getShortLink())
                .longLink(linkEntity.getLongLink())
                .creationDate(linkEntity.getCreationDate())
                .openCount(linkEntity.getClicks())
                .expirationDate(linkEntity.getExpirationDate())
                .username(linkEntity.getUser() != null ? linkEntity.getUser().getUsername() : null)
                .build();
    }
}
