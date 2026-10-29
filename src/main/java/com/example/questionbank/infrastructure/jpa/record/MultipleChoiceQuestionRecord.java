package com.example.questionbank.infrastructure.jpa.record;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA representation of a multiple choice question.
 */
@Entity
@Table(name = "multiple_choice_questions")
public class MultipleChoiceQuestionRecord extends QuestionRecord {

    /** One question has many options; removing the question removes its options. */
    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AnswerOptionRecord> options = new ArrayList<>();

    @Column(nullable = false)
    private String correctAnswer;

    public List<AnswerOptionRecord> getOptions() { return options; }
    public void setOptions(List<AnswerOptionRecord> options) { this.options = options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
