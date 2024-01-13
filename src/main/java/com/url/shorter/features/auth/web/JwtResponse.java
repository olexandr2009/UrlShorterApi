package com.url.shorter.features.auth.web;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private Integer id;
    private String username;
    private List<String> roles = new ArrayList<>();

    public JwtResponse() {
    }

    public JwtResponse(String token, Integer id, String username, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
