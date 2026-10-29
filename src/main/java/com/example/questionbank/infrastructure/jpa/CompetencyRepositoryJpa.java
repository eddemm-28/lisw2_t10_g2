package com.example.questionbank.infrastructure.jpa;

import com.example.questionbank.domain.model.Competency;
import com.example.questionbank.domain.repository.CompetencyRepository;
import com.example.questionbank.infrastructure.jpa.mapper.CompetencyMapper;
import com.example.questionbank.infrastructure.jpa.record.CompetencyRecord;
import com.example.questionbank.infrastructure.jpa.repository.SpringCompetencyDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the competency repository contract.
 */
@Repository
public class CompetencyRepositoryJpa implements CompetencyRepository {

    private final SpringCompetencyDataRepository springRepository;
    private final CompetencyMapper mapper;

    /**
     * Creates a JPA competency repository.
     *
     * @param springRepository Spring Data repository
     * @param mapper domain/JPA mapper
     */
    public CompetencyRepositoryJpa(SpringCompetencyDataRepository springRepository, CompetencyMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Competency save(Competency competency) {
        return mapper.toDomain(springRepository.save(mapper.toRecord(competency)));
    }

    @Override
    public Optional<Competency> findById(Long id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Competency> findAll() {
        return springRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Competency> findAllById(List<Long> ids) {
        return springRepository.findByIdIn(ids).stream().map(mapper::toDomain).toList();
    }
}
