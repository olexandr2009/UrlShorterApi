package com.url.shorter.features.user.services;

import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.dtos.UserDto;
import com.url.shorter.features.user.entities.RoleEntity;
import com.url.shorter.features.user.entities.UserEntity;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.exceptions.UserIncorrectPasswordException;
import com.url.shorter.features.user.exceptions.UserNotFoundException;
import com.url.shorter.features.user.repositories.RoleRepository;
import com.url.shorter.features.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testUpdateUser() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        when(userRepository.findByUsername(updateUserDto.getOldUsername()))
                .thenReturn(Optional.of(new UserEntity(updateUserDto.getOldUsername(), updateUserDto.getOldPassword())));
        when(userRepository.existsByUsername(updateUserDto.getNewUsername()))
                .thenReturn(false);
        when(userRepository.save(any()))
                .thenReturn(new UserEntity());
        when(encoder.matches(anyString(),anyString()))
                .thenReturn(true);

        assertEquals(new UserDto(), userService.updateUser(updateUserDto));
    }

    @Test
    void testRegisterUser() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(roleRepository.findByNames(anyCollection())).thenReturn(
                Set.of(new RoleEntity(RoleEntity.UserRole.ROLE_USER)));
        assertDoesNotThrow(() -> userService.registerUser("user", "pass"));
    }

    @Test
    void testRegisterUserThrowsUserAlreadyExistEx() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser("user", "pass"));
    }

    @Test
    void testUpdateUserThrowsUserNotFoundExByOldUsername() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        when(userRepository.findByUsername(updateUserDto.getOldUsername()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void testUpdateUserThrowsUserAlreadyExistExByNewUsername() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        when(userRepository.findByUsername(updateUserDto.getOldUsername()))
                .thenReturn(Optional.of(new UserEntity(updateUserDto.getOldUsername(), updateUserDto.getOldPassword())));
        when(userRepository.existsByUsername(updateUserDto.getNewUsername()))
                .thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void testUpdateUserThrowsUserIncorrectPasswordEx() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        when(userRepository.findByUsername(updateUserDto.getOldUsername()))
                .thenReturn(Optional.of(new UserEntity(updateUserDto.getOldUsername(), updateUserDto.getOldPassword())));
        when(userRepository.existsByUsername(updateUserDto.getNewUsername()))
                .thenReturn(false);
        when(encoder.encode(anyString()))
                .thenReturn("");

        assertThrows(UserIncorrectPasswordException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void testUpdateUserRolesThrowsUserNotFoundEx() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUserRoles("user", List.of()));
    }

    @Test
    void testUpdateUserRoles() {
        List<RoleEntity.UserRole> roles = List.of(
                RoleEntity.UserRole.ROLE_USER,
                RoleEntity.UserRole.ROLE_ADMIN
        );
        UserEntity userEntity = new UserEntity();
        Set<RoleEntity> roleEntities = Set.of(
                new RoleEntity(RoleEntity.UserRole.ROLE_USER),
                new RoleEntity(RoleEntity.UserRole.ROLE_ADMIN)
        );
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(new UserEntity()));
        userEntity.setRoles(roleEntities);
        when(userRepository.save(any()))
                .thenReturn(userEntity);
        when(roleRepository.findByNames(anyCollection()))
                .thenReturn(roleEntities);
        String username = "user";
        UserDto expected = new UserDto();
        expected.setRoles(new HashSet<>(roles));

        UserDto actual = userService.updateUserRoles(username, roles);
        assertEquals(expected, actual);
    }

    private UpdateUserDto newTestUpdateUserDto() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setNewUsername("newUsername");
        updateUserDto.setOldUsername("oldUsername");
        updateUserDto.setNewPassword("newPass");
        updateUserDto.setOldPassword("oldPass");
        return updateUserDto;
    }
}