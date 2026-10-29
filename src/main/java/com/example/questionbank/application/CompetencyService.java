package com.example.questionbank.application;

import com.example.questionbank.domain.model.Competency;
import com.example.questionbank.domain.repository.CompetencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Coordinates competency use cases.
 */
@Service
public class CompetencyService {

    private final CompetencyRepository competencyRepository;

    /**
     * Creates a competency service.
     *
     * @param competencyRepository competency persistence contract
     */
    public CompetencyService(CompetencyRepository competencyRepository) {
        this.competencyRepository = competencyRepository;
    }

    /**
     * Creates a competency.
     *
     * @param name competency name
     * @param description competency description
     * @return created competency
     */
    @Transactional
    public Competency create(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Competency name is required");
        }
        return competencyRepository.save(new Competency(null, name, description));
    }

    /**
     * Lists all competencies.
     *
     * @return competency list
     */
    public List<Competency> findAll() {
        return competencyRepository.findAll();
    }
}
