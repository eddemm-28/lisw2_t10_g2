package com.example.questionbank.infrastructure.jpa.repository;

import com.example.questionbank.domain.model.QuestionStatus;
import com.example.questionbank.infrastructure.jpa.record.QuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data repository for questions.
 */
public interface SpringQuestionDataRepository extends JpaRepository<QuestionRecord, Long> {
    List<QuestionRecord> findByAuthorId(Long authorId);
    List<QuestionRecord> findByStatus(QuestionStatus status);
    List<QuestionRecord> findByStatusAndCompetenciesId(QuestionStatus status, Long competencyId);
}
