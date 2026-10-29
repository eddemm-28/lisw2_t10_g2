package com.example.questionbank.domain.repository;

import com.example.questionbank.domain.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Technology independent contract for user persistence.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
}
