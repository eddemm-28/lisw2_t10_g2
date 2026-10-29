package com.example.questionbank.api.dto;

/**
 * Request body used to create a user.
 */
public record CreateUserRequest(String fullName, String email) {
}
