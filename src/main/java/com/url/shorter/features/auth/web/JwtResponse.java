package com.url.shorter.features.auth.web;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private UUID id;
    private String username;
    private List<String> roles = new ArrayList<>();

    public JwtResponse() {
    }

    public JwtResponse(String token, UUID id, String username, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
