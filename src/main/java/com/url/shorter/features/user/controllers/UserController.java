package com.url.shorter.features.user.controllers;

import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.dtos.UpdateUserRoleDto;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.exceptions.UserIncorrectPasswordException;
import com.url.shorter.features.user.exceptions.UserNotFoundException;
import com.url.shorter.features.user.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@Validated
@RestController
@RequestMapping("/V1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserAlreadyExistException, UserIncorrectPasswordException {
        return ResponseEntity.ok(userService.updateUser(updateUserDto));
    }

    @PutMapping("/update/roles")
    public ResponseEntity<UserDto> updateUserRole(@Valid @RequestBody UpdateUserRoleDto updateUserRoleDto, Principal principal)
            throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUserRoles(principal.getName(), updateUserRoleDto.getRoles()));
    }
}