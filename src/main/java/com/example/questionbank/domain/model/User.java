package com.example.questionbank.domain.model;

/**
 * Domain user of the question bank.
 * This class does not contain persistence annotations.
 */
public class User {
    private Long id;
    private String fullName;
    private String email;

    /**
     * Creates a user.
     *
     * @param id unique identifier
     * @param fullName user full name
     * @param email user email
     */
    public User(Long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
}
