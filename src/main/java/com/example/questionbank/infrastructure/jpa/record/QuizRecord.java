package com.example.questionbank.infrastructure.jpa.record;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA representation of a quiz.
 */
@Entity
@Table(name = "quizzes")
public class QuizRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    /** Many quizzes can contain many published questions. */
    @ManyToMany
    @JoinTable(
            name = "quiz_question",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<QuestionRecord> questions = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<QuestionRecord> getQuestions() { return questions; }
    public void setQuestions(List<QuestionRecord> questions) { this.questions = questions; }
}
