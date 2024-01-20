package com.url.shorter.features.user.dtos;

import com.url.shorter.features.user.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String username;
    private Set<RoleEntity.UserRole> roles = new HashSet<>();

    public UserDto(UUID id) {
        this.id = id;
    }
}