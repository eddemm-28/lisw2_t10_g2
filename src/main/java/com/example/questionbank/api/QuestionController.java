package com.example.questionbank.api;

import com.example.questionbank.api.dto.AnswerOptionRequest;
import com.example.questionbank.api.dto.CreateQuestionRequest;
import com.example.questionbank.application.QuestionService;
import com.example.questionbank.domain.model.AnswerOption;
import com.example.questionbank.domain.model.Question;
import com.example.questionbank.domain.model.QuestionStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API for question management.
 */
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    /**
     * Creates a question controller.
     *
     * @param questionService question service
     */
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Creates a question.
     *
     * @param request question data
     * @return created question
     */
    @PostMapping
    public ResponseEntity<Question> create(@RequestBody CreateQuestionRequest request) {
        List<AnswerOption> options = new ArrayList<>();
        if (request.options() != null) {
            for (AnswerOptionRequest optionRequest : request.options()) {
                options.add(new AnswerOption(null, optionRequest.text(), optionRequest.correct()));
            }
        }

        Question question = questionService.createQuestion(
                request.type(), request.statement(), request.difficulty(), request.authorId(),
                request.competencyIds(), options, request.correctAnswer(), request.expectedAnswer());

        return ResponseEntity.created(URI.create("/api/questions/" + question.getId())).body(question);
    }

    /**
     * Lists questions, optionally filtered by status.
     *
     * @param status optional status filter
     * @return question list
     */
    @GetMapping
    public ResponseEntity<List<Question>> findAll(
            @RequestParam(required = false) QuestionStatus status) {
        if (status == null) {
            return ResponseEntity.ok(questionService.findAll());
        }
        return ResponseEntity.ok(questionService.findByStatus(status));
    }

    /**
     * Finds a question by identifier.
     *
     * @param id question identifier
     * @return question
     */
    @GetMapping("/{id}")
    public ResponseEntity<Question> findById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }

    /**
     * Sends a question to review.
     *
     * @param id question identifier
     * @return updated question
     */
    @PostMapping("/{id}/review")
    public ResponseEntity<Question> sendToReview(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.sendToReview(id));
    }

    /**
     * Approves a question.
     *
     * @param id question identifier
     * @return updated question
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<Question> approve(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.approve(id));
    }

    /**
     * Publishes a question.
     *
     * @param id question identifier
     * @return updated question
     */
    @PostMapping("/{id}/publish")
    public ResponseEntity<Question> publish(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.publish(id));
    }

    /**
     * Rejects a question and returns it to DRAFT status.
     *
     * @param id question identifier
     * @return updated question
     */
    @PostMapping("/{id}/reject")
    public ResponseEntity<Question> reject(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.reject(id));
    }
}
