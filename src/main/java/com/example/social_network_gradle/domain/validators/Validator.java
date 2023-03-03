package com.example.social_network_gradle.domain.validators;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}