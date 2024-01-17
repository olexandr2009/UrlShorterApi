package com.url.shorter.features.user.controllers;

import com.url.shorter.config.jwt.JwtUtils;
import com.url.shorter.config.jwt.UserDetailsImpl;
import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.dtos.UpdateUserRoleDto;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.exceptions.UserIncorrectPasswordException;
import com.url.shorter.features.user.exceptions.UserNotFoundException;
import com.url.shorter.features.user.mapper.UserMapper;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Users", description = "User controller to manage usernames, passwords and roles")
@Validated
@RestController
@RequestMapping("/V1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserMapper userMapper;

    @Operation(
            summary = "Rename user and reset password to newer one",
            description = "Update password and username ",
            tags = {"Users"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202",
                    description = "Username, password changed",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "oldUsername not found or newUsername alreadyExists or oldPassword doesn't matches with existing"
            ),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "Object to update username and password", required = true)
            @Valid @RequestBody UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserAlreadyExistException, UserIncorrectPasswordException {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            UserDetailsImpl authentication = (UserDetailsImpl) context.getAuthentication().getPrincipal();
            return ResponseEntity.ok(userService.updateUser(authentication.getUuid(), updateUserDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Update user roles",
            description = "Update roles to access other urls",
            tags = {"Users"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "User",
                    content = @Content(
                            schema = @Schema(implementation = UserDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Roles cannot be Empty"
            ),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @PutMapping("/update/roles")
    public ResponseEntity<UserDto> updateUserRole(
            @Parameter(description = "List to update roles", required = true)
            @Valid @RequestBody UpdateUserRoleDto updateUserRoleDto)
            throws UserNotFoundException {
      try{
          SecurityContext context = SecurityContextHolder.getContext();
          UserDetailsImpl authentication = (UserDetailsImpl) context.getAuthentication().getPrincipal();
          return ResponseEntity.ok(userService.updateUserRoles(authentication.getUuid(), updateUserRoleDto.getRoles()));
      }catch (Exception e){
          return ResponseEntity.badRequest().build();
      }
    }
}