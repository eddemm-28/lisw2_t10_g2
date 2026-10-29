package com.example.questionbank.infrastructure.jpa.repository;

import com.example.questionbank.infrastructure.jpa.record.CompetencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data repository for competencies.
 */
public interface SpringCompetencyDataRepository extends JpaRepository<CompetencyRecord, Long> {
    List<CompetencyRecord> findByIdIn(List<Long> ids);
}
