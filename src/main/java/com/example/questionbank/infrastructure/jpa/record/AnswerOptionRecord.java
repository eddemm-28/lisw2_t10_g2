package com.example.questionbank.infrastructure.jpa.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * JPA representation of an answer option.
 */
@Entity
@Table(name = "answer_options")
public class AnswerOptionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean correct;

    /** Each option belongs to exactly one question. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private MultipleChoiceQuestionRecord question;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public MultipleChoiceQuestionRecord getQuestion() { return question; }
    public void setQuestion(MultipleChoiceQuestionRecord question) { this.question = question; }
}
