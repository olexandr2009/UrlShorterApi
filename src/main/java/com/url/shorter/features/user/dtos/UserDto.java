package com.url.shorter.features.user.dtos;

import com.url.shorter.features.user.entities.RoleEntity;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "User model")
@EqualsAndHashCode
@ToString
public class UserDto {
    @Schema(description = "Unique user id")
    private UUID id;
    @Schema(description = "Unique username")
    private String username;
    @Schema(description = "List of user roles")
    private Set<RoleEntity.UserRole> roles = new HashSet<>();

    public UserDto(UUID id) {
        this.id = id;
    }
}