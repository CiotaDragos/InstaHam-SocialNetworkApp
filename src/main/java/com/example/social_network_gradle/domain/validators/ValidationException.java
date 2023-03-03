package com.example.social_network_gradle.domain.validators;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
