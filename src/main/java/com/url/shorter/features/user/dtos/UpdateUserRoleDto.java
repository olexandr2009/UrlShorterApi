package com.url.shorter.features.user.dtos;

import com.url.shorter.features.user.entities.RoleEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UpdateUserRoleDto {

    @NotEmpty
    Set<RoleEntity.UserRole> roles = new HashSet<>();
}