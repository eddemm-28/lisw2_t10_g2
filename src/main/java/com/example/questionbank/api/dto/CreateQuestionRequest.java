package com.example.questionbank.api.dto;

import java.util.List;

/**
 * Request body used to create a question.
 */
public record CreateQuestionRequest(
        String type,
        String statement,
        String difficulty,
        Long authorId,
        List<Long> competencyIds,
        List<AnswerOptionRequest> options,
        String correctAnswer,
        Boolean expectedAnswer
) {
}
