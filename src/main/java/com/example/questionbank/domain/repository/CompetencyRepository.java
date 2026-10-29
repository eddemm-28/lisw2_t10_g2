package com.example.questionbank.domain.repository;

import com.example.questionbank.domain.model.Competency;
import java.util.List;
import java.util.Optional;

/**
 * Technology independent contract for competency persistence.
 */
public interface CompetencyRepository {
    Competency save(Competency competency);
    Optional<Competency> findById(Long id);
    List<Competency> findAll();
    List<Competency> findAllById(List<Long> ids);
}
