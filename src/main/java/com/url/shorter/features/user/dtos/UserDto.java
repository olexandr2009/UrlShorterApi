package com.url.shorter.features.user.dtos;

import com.url.shorter.features.user.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private Set<RoleEntity.UserRole> roles = new HashSet<>();
}