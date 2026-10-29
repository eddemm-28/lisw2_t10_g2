package com.example.questionbank.infrastructure.jpa;

import com.example.questionbank.domain.model.User;
import com.example.questionbank.domain.repository.UserRepository;
import com.example.questionbank.infrastructure.jpa.mapper.UserMapper;
import com.example.questionbank.infrastructure.jpa.record.UserRecord;
import com.example.questionbank.infrastructure.jpa.repository.SpringUserDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the user repository contract.
 */
@Repository
public class UserRepositoryJpa implements UserRepository {

    private final SpringUserDataRepository springRepository;
    private final UserMapper mapper;

    /**
     * Creates a JPA user repository.
     *
     * @param springRepository Spring Data repository
     * @param mapper domain/JPA mapper
     */
    public UserRepositoryJpa(SpringUserDataRepository springRepository, UserMapper mapper) {
        this.springRepository = springRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(springRepository.save(mapper.toRecord(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return springRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return springRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
