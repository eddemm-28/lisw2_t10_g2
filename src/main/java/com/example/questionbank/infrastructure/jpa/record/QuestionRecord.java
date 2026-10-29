package com.example.questionbank.infrastructure.jpa.record;

import com.example.questionbank.domain.model.QuestionStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Base JPA entity for the question inheritance hierarchy.
 * Uses the JOINED strategy: common data is stored in {@code questions}
 * and specialized data is stored in subclass tables.
 */
@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "question_type")
public abstract class QuestionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String statement;

    private String difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionStatus status;

    /** Many questions can have one author. The foreign key is stored here. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private UserRecord author;

    /** One question has one metadata record. Question is the owning side. */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "metadata_id", unique = true)
    private QuestionMetadataRecord metadata;

    /** Many questions can evaluate many competencies. */
    @ManyToMany
    @JoinTable(
            name = "question_competency",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "competency_id")
    )
    private List<CompetencyRecord> competencies = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public QuestionStatus getStatus() { return status; }
    public void setStatus(QuestionStatus status) { this.status = status; }
    public UserRecord getAuthor() { return author; }
    public void setAuthor(UserRecord author) { this.author = author; }
    public QuestionMetadataRecord getMetadata() { return metadata; }
    public void setMetadata(QuestionMetadataRecord metadata) { this.metadata = metadata; }
    public List<CompetencyRecord> getCompetencies() { return competencies; }
    public void setCompetencies(List<CompetencyRecord> competencies) { this.competencies = competencies; }
}
