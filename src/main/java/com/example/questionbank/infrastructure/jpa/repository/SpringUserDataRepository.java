package com.example.questionbank.infrastructure.jpa.repository;

import com.example.questionbank.infrastructure.jpa.record.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for users.
 */
public interface SpringUserDataRepository extends JpaRepository<UserRecord, Long> {
}
