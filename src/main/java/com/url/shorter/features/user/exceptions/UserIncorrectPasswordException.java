package com.url.shorter.features.user.exceptions;

public class UserIncorrectPasswordException extends RuntimeException {

    private static final String USER_INCORRECT_PASSWORD_EXCEPTION_TEXT =
            "Incorrect password for user with username = %s.";

    public UserIncorrectPasswordException(String username) {
        super(String.format(USER_INCORRECT_PASSWORD_EXCEPTION_TEXT, username));
    }
}