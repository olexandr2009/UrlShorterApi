package com.url.shorter.features.auth.web;

import com.url.shorter.features.user.entities.UserEntity;
import com.url.shorter.features.user.exceptions.UserAlreadyExistException;
import com.url.shorter.features.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest
@RunWith(JUnit4.class)
class AuthControllerIntegrationTest {
    private final String testUserName = "test_username";
    private final String testPassword = "test_password";

    @Autowired
    private AuthController authController;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearTableUsers() {
        userRepository.deleteAll();
    }

    @Test
    void testAuthenticateUserThrowsEx() {
        assertThrows(AuthenticationException.class,
                () -> authController.authenticateUser(
                        createTestLoginRequest(testUserName, testPassword)));
    }
    @Test
    void testRegisterUser() {
        assertEquals(ResponseEntity.accepted().build(), authController.registerUser(
                        createTestSignUpRequest(testUserName, testPassword)));
    }

    @Test
    void testRegisterUserThrowsEx() {
        userRepository.save(new UserEntity(testUserName, testPassword));
        assertThrows(UserAlreadyExistException.class, () -> authController.registerUser(
                createTestSignUpRequest(testUserName, testPassword)));

    }

    private LoginRequest createTestLoginRequest(String username, String password) {
        System.out.println("test running");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);
        return loginRequest;
    }

    private SignupRequest createTestSignUpRequest(String username, String password) {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword(password);
        signupRequest.setUsername(username);
        return signupRequest;
    }
}
