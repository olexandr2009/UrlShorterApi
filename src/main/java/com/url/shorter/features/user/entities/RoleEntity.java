package com.url.shorter.features.user.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private UserRole name;

    public RoleEntity() {
    }

    public RoleEntity(UserRole name) {
        this.name = name;
    }

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN
    }
}
