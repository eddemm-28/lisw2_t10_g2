package com.example.questionbank.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Multiple choice question with several answer options.
 */
public class MultipleChoiceQuestion extends Question {
    private List<AnswerOption> options = new ArrayList<>();
    private String correctAnswer;

    /**
     * Creates a multiple choice question.
     *
     * @param id unique identifier
     * @param statement question statement
     * @param difficulty question difficulty
     * @param status current lifecycle status
     * @param author question author
     * @param options available answer options
     * @param correctAnswer correct answer text
     */
    public MultipleChoiceQuestion(Long id, String statement, String difficulty, QuestionStatus status,
                                  User author, List<AnswerOption> options, String correctAnswer) {
        super(id, statement, difficulty, status, author);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public List<AnswerOption> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
}
