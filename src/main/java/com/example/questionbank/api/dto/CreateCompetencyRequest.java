package com.example.questionbank.api.dto;

/**
 * Request body used to create a competency.
 */
public record CreateCompetencyRequest(String name, String description) {
}
