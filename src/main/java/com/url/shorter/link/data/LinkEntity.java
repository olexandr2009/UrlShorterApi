package com.url.shorter.link.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Link entity
 */
@Entity
@Table(name = "links")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkEntity {
    @Id
    @Column(name = "short_link")
    String shortLink;

    @Column(name = "original_link")
    String originalLink;

    @Column(name = "creation_date")
    LocalDateTime creationDate;

    @Column(name = "open_counter")
    Integer openCounter;

    @Column(name = "creator_name")
    String creatorName;

    @Column(name = "valid_until")
    LocalDateTime validUntil;
}
