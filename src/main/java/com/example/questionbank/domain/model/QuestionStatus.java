package com.example.questionbank.domain.model;

/**
 * Lifecycle states of a question.
 */
public enum QuestionStatus {
    DRAFT,
    PENDING_REVIEW,
    IN_REVIEW,
    APPROVED,
    PUBLISHED,
    ARCHIVED
}
