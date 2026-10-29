package com.example.questionbank.domain.repository;

import com.example.questionbank.domain.model.Quiz;
import java.util.List;
import java.util.Optional;

/**
 * Technology independent contract for quiz persistence.
 */
public interface QuizRepository {
    Quiz save(Quiz quiz);
    Optional<Quiz> findById(Long id);
    List<Quiz> findAll();
}
