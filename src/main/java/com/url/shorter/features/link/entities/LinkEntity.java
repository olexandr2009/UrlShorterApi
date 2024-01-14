package com.url.shorter.features.link.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @Column(name = "id")
    UUID id;

    @Column(name = "short_url", length = 25)
    @NotNull
    String shortUrl;

    @Column(name = "origin_url", length = 250)
    @NotNull
    String originUrl;

    @Column(name = "created_date")
    @NotNull
    LocalDateTime createdDate;

    @Column(name = "open_count")
    @NotNull
    Integer openCount;

    @Column(name = "expiration_date")
    @NotNull
    LocalDateTime expirationDate;

    @Column(name = "user_id")
    @NotNull
    UUID userId;

}
