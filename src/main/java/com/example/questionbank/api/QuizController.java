package com.example.questionbank.api;

import com.example.questionbank.api.dto.CreateQuizRequest;
import com.example.questionbank.application.QuizService;
import com.example.questionbank.domain.model.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST API for quiz management.
 */
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    /**
     * Creates a quiz controller.
     *
     * @param quizService quiz service
     */
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Creates a quiz.
     *
     * @param request quiz data
     * @return created quiz
     */
    @PostMapping
    public ResponseEntity<Quiz> create(@RequestBody CreateQuizRequest request) {
        Quiz quiz = quizService.createQuiz(request.title(), request.questionIds());
        return ResponseEntity.created(URI.create("/api/quizzes/" + quiz.getId())).body(quiz);
    }

    /**
     * Lists all quizzes.
     *
     * @return quiz list
     */
    @GetMapping
    public ResponseEntity<List<Quiz>> findAll() {
        return ResponseEntity.ok(quizService.findAll());
    }

    /**
     * Finds a quiz by identifier.
     *
     * @param id quiz identifier
     * @return quiz
     */
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> findById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.findById(id));
    }
}
