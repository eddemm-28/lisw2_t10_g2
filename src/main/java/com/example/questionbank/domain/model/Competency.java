package com.example.questionbank.domain.model;

/**
 * Academic competency that can be evaluated by questions.
 */
public class Competency {
    private Long id;
    private String name;
    private String description;

    /**
     * Creates a competency.
     *
     * @param id unique identifier
     * @param name competency name
     * @param description competency description
     */
    public Competency(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
