package com.url.shorter.features.user.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND_EXCEPTION_TEXT = "User with username = %s not found.";
    private static final String USER_WITH_ID_NOT_FOUND_EXCEPTION_TEXT = "User with id = %s not found.";

    public UserNotFoundException(UUID uuid) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_TEXT, uuid));
    }

    public UserNotFoundException(String  username) {
        super(String.format(USER_WITH_ID_NOT_FOUND_EXCEPTION_TEXT, username));
    }
}