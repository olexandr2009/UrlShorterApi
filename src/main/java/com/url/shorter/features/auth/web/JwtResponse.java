package com.url.shorter.features.auth.web;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response for authorize")
public class JwtResponse {
    @Schema(description = "Jwt token to authorize with")
    private String token;

    @Schema(description = "User id")
    private UUID id;

    @Size(min = 8, max = 100)
    @Schema(description = "Logged Username")
    private String username;

    @Schema(description = "Username to login with")
    private List<String> roles = new ArrayList<>();
}
