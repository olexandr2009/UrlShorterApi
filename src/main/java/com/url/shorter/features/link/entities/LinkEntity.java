package com.url.shorter.features.link.entities;

import com.url.shorter.features.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "l_link_s_link")
public class LinkEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "long_link", nullable = false)
        private String longLink;

        @Column(name = "short_link", nullable = false)
        private String shortLink;

        @Column(name = "creation_date", nullable = false)
        private LocalDateTime creationDate;

        @Column(name = "expiration_date", nullable = false)
        private LocalDateTime expirationDate;

        @Column(name = "clicks", nullable = false)
        private int clicks;

        @ManyToOne
        @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
        private UserEntity user;

        public LinkEntity(UUID id, String longLink, String shortLink, UserEntity user, LocalDateTime creationDate, LocalDateTime expirationDate, int clicks) {
                this.id = id;
                this.longLink = longLink;
                this.shortLink = shortLink;
                this.user = user;
                this.creationDate = creationDate;
                this.expirationDate = expirationDate;
                this.clicks = clicks;
        }

        public LinkEntity(String shortLink, String longLink) {
        }
}
