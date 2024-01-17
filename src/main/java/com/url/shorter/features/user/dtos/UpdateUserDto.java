package com.url.shorter.features.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dto to update user")
public class UpdateUserDto {
    @Size(min = 8, max = 100)
    private String oldUsername;

    @Size(min = 8, max = 100)
    private String oldPassword;

    @Size(min = 8, max = 100)
    private String newUsername;

    @Size(min = 8, max = 100)
    private String newPassword;
}