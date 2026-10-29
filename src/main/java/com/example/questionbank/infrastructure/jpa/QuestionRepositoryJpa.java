package com.example.questionbank.infrastructure.jpa;

import com.example.questionbank.domain.model.Question;
import com.example.questionbank.domain.model.QuestionStatus;
import com.example.questionbank.domain.repository.QuestionRepository;
import com.example.questionbank.infrastructure.jpa.mapper.QuestionMapper;
import com.example.questionbank.infrastructure.jpa.record.QuestionRecord;
import com.example.questionbank.infrastructure.jpa.repository.SpringQuestionDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the question repository contract.
 */
@Repository
public class QuestionRepositoryJpa implements QuestionRepository {

    private final SpringQuestionDataRepository springRepository;
    private final QuestionMapper mapper;

    /**
     * Creates a JPA question repository.
     *
     * @param springRepository Spring Data repository
     * @param mapper domain/JPA mapper
     */
    public QuestionRepositoryJpa(SpringQuestionDataRepository springRepository, QuestionMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public Question save(Question question) {
        QuestionRecord saved = springRepository.save(mapper.toRecord(question));
        return mapper.toDomain(springRepository.findById(saved.getId()).orElse(saved));
    }

    @Override
    public Optional<Question> findById(Long id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Question> findAll() {
        return springRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Question> findByAuthorId(Long authorId) {
        return springRepository.findByAuthorId(authorId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Question> findByStatus(QuestionStatus status) {
        return springRepository.findByStatus(status).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Question> findPublishedByCompetencyId(Long competencyId) {
        return springRepository
                .findByStatusAndCompetenciesId(QuestionStatus.PUBLISHED, competencyId)
                .stream().map(mapper::toDomain).toList();
    }
}
