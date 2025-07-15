package com.marketplace.demo.configs;

public class UsernameNotValidException extends RuntimeException {

    public UsernameNotValidException(String message) {
        super(message);
    }

    public UsernameNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotValidException() {
        super("Username is not valid");
    }
}
