package com.example.questionbank.infrastructure.jpa.record;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA representation of complementary question metadata.
 */
@Entity
@Table(name = "question_metadata")
public class QuestionMetadataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cognitiveLevel;
    private int estimatedTimeMinutes;
    private LocalDateTime lastReviewDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCognitiveLevel() { return cognitiveLevel; }
    public void setCognitiveLevel(String cognitiveLevel) { this.cognitiveLevel = cognitiveLevel; }
    public int getEstimatedTimeMinutes() { return estimatedTimeMinutes; }
    public void setEstimatedTimeMinutes(int estimatedTimeMinutes) { this.estimatedTimeMinutes = estimatedTimeMinutes; }
    public LocalDateTime getLastReviewDate() { return lastReviewDate; }
    public void setLastReviewDate(LocalDateTime lastReviewDate) { this.lastReviewDate = lastReviewDate; }
}
