package com.url.shorter.features.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String oldUsername;
    private String oldPassword;
    private String newUsername;
    private String newPassword;
}