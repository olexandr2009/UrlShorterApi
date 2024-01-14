package com.url.shorter.features.link.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


import com.url.shorter.features.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.security.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "l_link_s_link")
public class LinkEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private String id = UUID.randomUUID().toString();

        @Column(name = "long_link", nullable = false)
        private String longLink;

        @Column(name = "short_link", nullable = false)
        private String shortLink;

        @Column(name = "id_user", nullable = false)
        private int userId;

        @Column(name = "creation_date", nullable = false)
        private Timestamp creationDate;

        @Column(name = "expiration_date", nullable = false)
        private Timestamp expirationDate;

        @Column(name = "clicks", nullable = false)
        private int clicks;

        @ManyToOne
        @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
        private UserEntity user;

        public LinkEntity(String longLink, String shortLink, int userId, Timestamp creationDate, Timestamp expirationDate, int clicks, UserEntity user) {
                this.longLink = longLink;
                this.shortLink = shortLink;
                this.userId = userId;
                this.creationDate = creationDate;
                this.expirationDate = expirationDate;
                this.clicks = clicks;
                this.user = user;
        }
}
