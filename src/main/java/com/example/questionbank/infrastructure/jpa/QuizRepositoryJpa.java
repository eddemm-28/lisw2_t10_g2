package com.example.questionbank.infrastructure.jpa;

import com.example.questionbank.domain.model.Quiz;
import com.example.questionbank.domain.repository.QuizRepository;
import com.example.questionbank.infrastructure.jpa.mapper.QuizMapper;
import com.example.questionbank.infrastructure.jpa.record.QuizRecord;
import com.example.questionbank.infrastructure.jpa.repository.SpringQuizDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the quiz repository contract.
 */
@Repository
public class QuizRepositoryJpa implements QuizRepository {

    private final SpringQuizDataRepository springRepository;
    private final QuizMapper mapper;

    /**
     * Creates a JPA quiz repository.
     *
     * @param springRepository Spring Data repository
     * @param mapper domain/JPA mapper
     */
    public QuizRepositoryJpa(SpringQuizDataRepository springRepository, QuizMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Quiz save(Quiz quiz) {
        QuizRecord saved = springRepository.save(mapper.toRecord(quiz));
        return mapper.toDomain(springRepository.findById(saved.getId()).orElse(saved));
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Quiz> findAll() {
        return springRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
