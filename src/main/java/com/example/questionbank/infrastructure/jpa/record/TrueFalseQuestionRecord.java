package com.example.questionbank.infrastructure.jpa.record;

import jakarta.persistence.*;

/**
 * JPA representation of a true/false question.
 */
@Entity
@Table(name = "true_false_questions")
public class TrueFalseQuestionRecord extends QuestionRecord {

    @Column(nullable = false)
    private boolean expectedAnswer;

    public boolean isExpectedAnswer() { return expectedAnswer; }
    public void setExpectedAnswer(boolean expectedAnswer) { this.expectedAnswer = expectedAnswer; }
}
