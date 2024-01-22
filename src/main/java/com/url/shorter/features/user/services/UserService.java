package com.url.shorter.features.user.services;

import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.entities.RoleEntity;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.exceptions.UserIncorrectPasswordException;
import com.url.shorter.features.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Collection;

public interface UserService {
    @Transactional
    void registerUser(String username, String password)
            throws UserAlreadyExistException;

    @Transactional
    UserDto updateUser(UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserIncorrectPasswordException, UserAlreadyExistException;

    @Transactional
    UserDto updateUserRoles(String username, Collection<RoleEntity.UserRole> roles)
            throws UserNotFoundException;

    UserDto findByUsername(String username);
}
