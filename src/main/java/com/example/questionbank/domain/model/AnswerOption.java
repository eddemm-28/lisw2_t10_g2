package com.example.questionbank.domain.model;

/**
 * Answer option associated with a multiple choice question.
 */
public class AnswerOption {
    private Long id;
    private String text;
    private boolean correct;

    /**
     * Creates an answer option.
     *
     * @param id unique identifier
     * @param text option text
     * @param correct indicates whether this is the correct option
     */
    public AnswerOption(Long id, String text, boolean correct) {
        this.id = id;
        this.text = text;
        this.correct = correct;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public boolean isCorrect() { return correct; }
}
