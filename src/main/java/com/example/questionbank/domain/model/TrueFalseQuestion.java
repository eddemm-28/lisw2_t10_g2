package com.example.questionbank.domain.model;

/**
 * True or false question.
 */
public class TrueFalseQuestion extends Question {
    private boolean expectedAnswer;

    /**
     * Creates a true/false question.
     *
     * @param id unique identifier
     * @param statement question statement
     * @param difficulty question difficulty
     * @param status current lifecycle status
     * @param author question author
     * @param expectedAnswer expected truth value
     */
    public TrueFalseQuestion(Long id, String statement, String difficulty, QuestionStatus status,
                             User author, boolean expectedAnswer) {
        super(id, statement, difficulty, status, author);
        this.expectedAnswer = expectedAnswer;
    }

    public boolean isExpectedAnswer() { return expectedAnswer; }
}
