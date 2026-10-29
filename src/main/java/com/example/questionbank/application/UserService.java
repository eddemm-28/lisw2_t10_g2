package com.example.questionbank.application;

import com.example.questionbank.domain.model.User;
import com.example.questionbank.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Coordinates user use cases.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Creates a user service.
     *
     * @param userRepository user persistence contract
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a user.
     *
     * @param fullName user full name
     * @param email user email
     * @return created user
     */
    @Transactional
    public User create(String fullName, String email) {
        if (fullName == null || fullName.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("Full name and email are required");
        }
        return userRepository.save(new User(null, fullName, email));
    }

    /**
     * Lists all users.
     *
     * @return user list
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
