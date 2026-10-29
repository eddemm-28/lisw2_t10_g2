package com.example.questionbank.api.dto;

import java.util.List;

/**
 * Request body used to create a quiz.
 */
public record CreateQuizRequest(String title, List<Long> questionIds) {
}
