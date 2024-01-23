package com.url.shorter.features.user.dtos;

import com.url.shorter.features.user.entities.RoleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Dto to update user roles")
public class UpdateUserRoleDto {

    @NotEmpty
    @Schema(description = "List of roles names")
    Set<RoleEntity.UserRole> roles = new HashSet<>();
}