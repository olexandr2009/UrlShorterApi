package com.url.shorter.features.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.url.shorter.features.user.dtos.UpdateUserDto;
import com.url.shorter.features.user.dtos.UpdateUserRoleDto;
import com.url.shorter.features.user.entities.RoleEntity;
import com.url.shorter.features.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(MockitoJUnitRunner.class)
class UserControllerUnitTest {
    @MockBean
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    @WithMockUser
    void testUpdateUserWorksCorrectly() throws Exception {
        int status = mvc.perform(put("/V1/users/update").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newTestUpdateUserDto())))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @WithMockUser
    void testUpdateUserBadRequest() throws Exception {
        int status = mvc.perform(put("/V1/users/update/roles").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(null)))
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @WithMockUser
    void updateUserRole() throws Exception {
        int status = mvc.perform(put("/V1/users/update/roles").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newTestUpdateRolesDto())))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    private UpdateUserRoleDto newTestUpdateRolesDto() {
        UpdateUserRoleDto updateUserRoleDto = new UpdateUserRoleDto();
        updateUserRoleDto.setRoles(Set.of(
                RoleEntity.UserRole.ROLE_USER,
                RoleEntity.UserRole.ROLE_ADMIN
        ));
        return updateUserRoleDto;
    }

    private UpdateUserDto newTestUpdateUserDto() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setNewPassword("newPassword");
        updateUserDto.setOldPassword("oldPassword");
        updateUserDto.setOldUsername("oldUsername");
        updateUserDto.setNewUsername("newUsername");
        return updateUserDto;
    }

    public static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}