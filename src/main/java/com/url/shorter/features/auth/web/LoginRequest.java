package com.url.shorter.features.auth.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to login into Api")
public class LoginRequest {

    @NotBlank
    @Size(min = 8, max = 100)
    @Schema(description = "Username to login with")
    private String username;

    @NotBlank
    @Size(min = 8, max = 100)
    @Schema(description = "User password to login with")
    private String password;
}