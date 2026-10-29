package com.example.questionbank.api.dto;

/**
 * Request body used to create an answer option.
 */
public record AnswerOptionRequest(String text, boolean correct) {
}
