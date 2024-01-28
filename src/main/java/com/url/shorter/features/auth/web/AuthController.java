package com.url.shorter.features.auth.web;

import com.url.shorter.config.jwt.JwtUtils;
import com.url.shorter.config.jwt.UserDetailsImpl;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "Authentication", description = "Authentication controller to get JWT token")
@Validated
@RestController
@RequestMapping("/V1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Operation(
            summary = "Login user",
            description = "Login user by username and password and get Jwt token",
            tags = {"Authentication"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = JwtResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Cannot login")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @Parameter(description = "Request with username and password to login", required = true)
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .ok(new JwtResponse(jwt, userDetails.getUuid(), userDetails.getUsername(), roles));
        } catch (Exception e){
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Register user",
            description = "Register user by username and password",
            tags = {"Authentication"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202"),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Parameter(description = "Request with username and password to register", required = true)
            @Valid @RequestBody SignupRequest signUpRequest
    ) {
        try{
            userService.registerUser(signUpRequest.getUsername(), signUpRequest.getPassword());
        } catch (UserAlreadyExistException e){
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
        return ResponseEntity.accepted().build();
    }
}
