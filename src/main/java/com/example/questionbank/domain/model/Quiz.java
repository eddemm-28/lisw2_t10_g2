package com.example.questionbank.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Quiz composed only by published questions.
 */
public class Quiz {
    private Long id;
    private String title;
    private List<Question> questions = new ArrayList<>();

    /**
     * Creates a quiz.
     *
     * @param id unique identifier
     * @param title quiz title
     * @param questions questions included in the quiz
     */
    public Quiz(Long id, String title, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public List<Question> getQuestions() { return questions; }
}
