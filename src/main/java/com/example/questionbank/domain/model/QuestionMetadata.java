package com.example.questionbank.domain.model;

import java.time.LocalDateTime;

/**
 * Complementary metadata for a question.
 */
public class QuestionMetadata {
    private Long id;
    private String cognitiveLevel;
    private int estimatedTimeMinutes;
    private LocalDateTime lastReviewDate;

    /**
     * Creates question metadata.
     *
     * @param id unique identifier
     * @param cognitiveLevel Bloom taxonomy level
     * @param estimatedTimeMinutes estimated response time
     * @param lastReviewDate date of the last review
     */
    public QuestionMetadata(Long id, String cognitiveLevel, int estimatedTimeMinutes, LocalDateTime lastReviewDate) {
        this.id = id;
        this.cognitiveLevel = cognitiveLevel;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.lastReviewDate = lastReviewDate;
    }


    public Long getId() { return id; }
    public String getCognitiveLevel() { return cognitiveLevel; }
    public int getEstimatedTimeMinutes() { return estimatedTimeMinutes; }
    public LocalDateTime getLastReviewDate() { return lastReviewDate; }
    
    public void setLastReviewDate(LocalDateTime lastReviewDate) {
    this.lastReviewDate = lastReviewDate;
    }
}
