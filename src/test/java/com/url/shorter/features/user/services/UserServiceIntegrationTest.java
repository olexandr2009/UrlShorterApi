package com.url.shorter.features.user.services;

import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.entities.RoleEntity;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.exceptions.UserIncorrectPasswordException;
import com.url.shorter.features.user.exceptions.UserNotFoundException;
import com.url.shorter.features.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void dropTable() {
        userRepository.deleteAll();
    }

    @Test
    void testUpdateUser() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        userService.registerUser(updateUserDto.getOldUsername(), updateUserDto.getOldPassword());
        assertEquals(updateUserDto.getNewUsername(), userService.updateUser(updateUserDto).getUsername());
    }

    @Test
    void testRegisterUser() {
        assertDoesNotThrow(() -> userService.registerUser("user", "pass"));
    }

    @Test
    void testRegisterUserThrowsUserAlreadyExistEx() {
        String pass = "pass";
        String username = "user";
        userService.registerUser(username, pass);
        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(username, pass));
    }

    @Test
    void testUpdateUserThrowsUserNotFoundExByOldUsername() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void testUpdateUserThrowsUserAlreadyExistExByNewUsername() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        userService.registerUser(updateUserDto.getOldUsername(), updateUserDto.getOldPassword());
        userService.registerUser(updateUserDto.getNewUsername(), updateUserDto.getNewPassword());

        assertThrows(UserAlreadyExistException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void testUpdateUserThrowsUserIncorrectPasswordEx() {
        UpdateUserDto updateUserDto = newTestUpdateUserDto();
        userService.registerUser(updateUserDto.getOldUsername(), "null");
        assertThrows(UserIncorrectPasswordException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void testUpdateUserRolesThrowsUserNotFoundEx() {
        assertThrows(UserNotFoundException.class, () -> userService.updateUserRoles("user", List.of()));
    }

    @Test
    void testUpdateUserRoles() {
        Set<RoleEntity.UserRole> roles = Set.of(
                RoleEntity.UserRole.ROLE_ADMIN,
                RoleEntity.UserRole.ROLE_USER
        );
        String username = "user";

        userService.registerUser(username, "pass");

        assertEquals(roles, userService.updateUserRoles(username, roles).getRoles());
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
