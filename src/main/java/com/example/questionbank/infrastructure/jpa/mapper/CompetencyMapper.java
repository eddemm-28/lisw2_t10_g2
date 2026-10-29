package com.example.questionbank.infrastructure.jpa.mapper;

import com.example.questionbank.domain.model.Competency;
import com.example.questionbank.infrastructure.jpa.record.CompetencyRecord;
import org.springframework.stereotype.Component;

/**
 * Maps between the Competency domain model and its JPA representation.
 */
@Component
public class CompetencyMapper {
    /**
     * Converts a domain competency to a JPA record.
     *
     * @param domain domain competency
     * @return JPA competency record
     */
    public CompetencyRecord toRecord(Competency domain) {
        CompetencyRecord record = new CompetencyRecord();
        record.setId(domain.getId());
        record.setName(domain.getName());
        record.setDescription(domain.getDescription());
        return record;
    }

    /**
     * Converts a JPA record to a domain competency.
     *
     * @param record JPA competency record
     * @return domain competency
     */
    public Competency toDomain(CompetencyRecord record) {
        return new Competency(record.getId(), record.getName(), record.getDescription());
    }
}
