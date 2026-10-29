package com.example.questionbank.infrastructure.jpa.mapper;

import com.example.questionbank.domain.model.User;
import com.example.questionbank.infrastructure.jpa.record.UserRecord;
import org.springframework.stereotype.Component;

/**
 * Maps between the User domain model and its JPA representation.
 */
@Component
public class UserMapper {
    /**
     * Converts a domain user to a JPA record.
     *
     * @param domain domain user
     * @return JPA user record
     */
    public UserRecord toRecord(User domain) {
        UserRecord record = new UserRecord();
        record.setId(domain.getId());
        record.setFullName(domain.getFullName());
        record.setEmail(domain.getEmail());
        return record;
    }

    /**
     * Converts a JPA record to a domain user.
     *
     * @param record JPA user record
     * @return domain user
     */
    public User toDomain(UserRecord record) {
        return new User(record.getId(), record.getFullName(), record.getEmail());
    }
}
