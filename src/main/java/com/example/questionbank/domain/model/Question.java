package com.example.questionbank.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Base domain class for all question types.
 * It is intentionally free from JPA annotations.
 */
public abstract class Question {
    private Long id;
    private String statement;
    private String difficulty;
    private QuestionStatus status;
    private User author;
    private QuestionMetadata metadata;
    private List<Competency> competencies = new ArrayList<>();

    /**
     * Creates a base question.
     *
     * @param id unique identifier
     * @param statement question statement
     * @param difficulty question difficulty
     * @param status current lifecycle status
     * @param author question author
     */
    protected Question(Long id, String statement, String difficulty, QuestionStatus status, User author) {
        this.id = id;
        this.statement = statement;
        this.difficulty = difficulty;
        this.status = status;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatement() { return statement; }
    public String getDifficulty() { return difficulty; }
    public QuestionStatus getStatus() { return status; }
    public void setStatus(QuestionStatus status) { this.status = status; }
    public User getAuthor() { return author; }
    public QuestionMetadata getMetadata() { return metadata; }
    public void setMetadata(QuestionMetadata metadata) { this.metadata = metadata; }
    public List<Competency> getCompetencies() { return competencies; }
    public void setCompetencies(List<Competency> competencies) { this.competencies = competencies; }
}
