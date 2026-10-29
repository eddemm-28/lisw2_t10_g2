package com.example.questionbank.domain.repository;

import com.example.questionbank.domain.model.Question;
import com.example.questionbank.domain.model.QuestionStatus;

import java.util.List;
import java.util.Optional;

/**
 * Technology independent contract for question persistence.
 */
public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(Long id);
    List<Question> findAll();
    List<Question> findByAuthorId(Long authorId);
    List<Question> findByStatus(QuestionStatus status);
    List<Question> findPublishedByCompetencyId(Long competencyId);
}
