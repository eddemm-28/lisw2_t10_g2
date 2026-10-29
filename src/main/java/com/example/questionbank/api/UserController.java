package com.example.questionbank.api;

import com.example.questionbank.api.dto.CreateUserRequest;
import com.example.questionbank.application.UserService;
import com.example.questionbank.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST API for user management.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Creates a user controller.
     *
     * @param userService user service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a user.
     *
     * @param request user data
     * @return created user
     */
    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserRequest request) {
        User user = userService.create(request.fullName(), request.email());
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).body(user);
    }

    /**
     * Lists all users.
     *
     * @return user list
     */
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
