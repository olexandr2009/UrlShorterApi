package com.url.shorter.link.servise.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Link DTO (data transfer object)
 */
@Data
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    String shortLink;
    String originalLink;
    LocalDateTime creationDate;
    Integer openCounter;
    String creatorName;
    LocalDateTime validUntil;

}
