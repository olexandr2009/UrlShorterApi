package com.url.shorter.features.link.dto;

import lombok.AccessLevel;
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
//@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    UUID id;
    String shortUrl;
    String originUrl;
    LocalDateTime creationDate;
    Integer openCount;
    LocalDateTime expirationDate;
    UUID userId;

}
