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
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserAlreadyExistException, UserIncorrectPasswordException {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsImpl authentication = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.updateUser(authentication.getId(), updateUserDto));
    }

    @PutMapping("/update/roles")
    public ResponseEntity<UserDto> updateUserRole(@Valid @RequestBody UpdateUserRoleDto updateUserRoleDto)
            throws UserNotFoundException {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsImpl authentication = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.updateUserRoles(authentication.getId(), updateUserRoleDto.getRoles()));
    }
}