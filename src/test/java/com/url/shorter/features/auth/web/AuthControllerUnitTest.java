package com.url.shorter.features.auth.web;

import com.url.shorter.config.jwt.JwtUtils;
import com.url.shorter.config.jwt.UserDetailsImpl;
import com.url.shorter.features.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AuthControllerUnitTest {
    @MockBean
    private UserService userService;
    @Autowired
    private AuthController authController;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testAuthenticateUser() {
        String testToken = "JSDGGHFVHFJHSFTYHJDJG24VGHV";
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(new UserDetailsImpl(UUID.randomUUID(), "", List.of()), ""));
        when(jwtUtils.generateJwtToken(any()))
                .thenReturn(testToken);

        LoginRequest loginRequest = createTestLoginRequest();

        assertEquals(testToken, authController.authenticateUser(loginRequest).getBody().getToken());
    }

    @Test
    void testAuthenticateUserThrowsException() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("bad credentials"));
        LoginRequest loginRequest = createTestLoginRequest();

        assertThrows(AuthenticationException.class, () -> authController.authenticateUser(loginRequest));
    }

    @Test
    void testRegisterUser() {
        doNothing().when(userService).registerUser(anyString(), anyString());

        SignupRequest signUpRequest = createTestSignUpRequest();
        assertEquals(ResponseEntity.accepted().build(), authController.registerUser(signUpRequest));
    }
    private LoginRequest createTestLoginRequest() {
        System.out.println("testing");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("test_password");
        loginRequest.setUsername("test_username");
        return loginRequest;
    }
    private SignupRequest createTestSignUpRequest() {
        System.out.println("testing");
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("test_password");
        signupRequest.setUsername("test_username");
        return signupRequest;
    }
}