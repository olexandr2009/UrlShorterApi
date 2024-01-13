package com.url.shorter.features.user.services;

import com.url.shorter.config.jwt.UserDetailsImpl;
import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.entities.RoleEntity;
import com.url.shorter.features.user.entities.UserEntity;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.exceptions.UserIncorrectPasswordException;
import com.url.shorter.features.user.exceptions.UserNotFoundException;
import com.url.shorter.features.user.mapper.UserMapper;
import com.url.shorter.features.user.repositories.RoleRepository;
import com.url.shorter.features.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Primary
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        userRepository.save(user);

        return UserDetailsImpl.build(user);
    }

    @Override
    @Transactional
    public void registerUser(String username, String password) throws UserAlreadyExistException {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException(username);
        }

        UserEntity user = new UserEntity(username, encoder.encode(password));
        Set<RoleEntity> roleEntities = roleRepository.findByNames(Collections.singleton(RoleEntity.UserRole.ROLE_USER));
        user.setRoles(roleEntities);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Integer userId, UpdateUserDto updateUserDto)
            throws UserNotFoundException, UserIncorrectPasswordException, UserAlreadyExistException {
        UserEntity user = userRepository.findByUsername(updateUserDto.getOldUsername())
                .orElseThrow(() -> new UserNotFoundException(updateUserDto.getOldUsername()));
        if (userRepository.existsByUsername(updateUserDto.getNewUsername())) {
            throw new UserAlreadyExistException(updateUserDto.getOldUsername());
        }
        if (user.getPassword().equals(encoder.encode(updateUserDto.getOldPassword())) &&
                Objects.nonNull(userId) && userId.equals(user.getId())) {
            user.setUsername(updateUserDto.getNewUsername());
            user.setPassword(encoder.encode(updateUserDto.getNewPassword()));
            return userMapper.toUserDto(userRepository.save(user));
        } else {
            throw new UserIncorrectPasswordException(updateUserDto.getOldUsername());
        }
    }

    @Override
    @Transactional
    public UserDto updateUserRoles(Integer userId, Collection<RoleEntity.UserRole> roles) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Set<RoleEntity> roleEntities = roleRepository.findByNames(roles);
        user.setRoles(roleEntities);
        return userMapper.toUserDto(userRepository.save(user));
    }

}