package com.url.shorter.features.user.mapper;

import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.entities.UserEntity;
import org.springframework.stereotype.Service;
import com.url.shorter.features.user.entities.RoleEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserDto toUserDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles().stream()
                .map(RoleEntity::getName).collect(Collectors.toSet()));
        return dto;
    }

    public List<UserDto> toUserDtos(Collection<UserEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream().map(this::toUserDto).toList();
    }
}
