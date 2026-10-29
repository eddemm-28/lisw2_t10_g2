package com.example.questionbank.application;

import com.example.questionbank.domain.model.Question;
import com.example.questionbank.domain.model.QuestionStatus;
import com.example.questionbank.domain.model.Quiz;
import com.example.questionbank.domain.repository.QuestionRepository;
import com.example.questionbank.domain.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates quiz use cases and publication rules.
 */
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    /**
     * Creates a quiz service.
     *
     * @param quizRepository quiz persistence contract
     * @param questionRepository question persistence contract
     */
    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Creates a quiz using only published questions.
     *
     * @param title quiz title
     * @param questionIds identifiers of the questions to include
     * @return created quiz
     */
    @Transactional
    public Quiz createQuiz(String title, List<Long> questionIds) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Quiz title is required");
        }
        if (questionIds == null || questionIds.isEmpty()) {
            throw new IllegalArgumentException("A quiz requires at least one question");
        }

        List<Question> questions = new ArrayList<>();
        for (Long id : questionIds) {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Question not found: " + id));
            if (question.getStatus() != QuestionStatus.PUBLISHED) {
                throw new IllegalStateException("Question " + id + " is not published");
            }
            questions.add(question);
        }

        return quizRepository.save(new Quiz(null, title, questions));
    }

    /**
     * Lists all quizzes.
     *
     * @return all quizzes
     */
    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    /**
     * Finds a quiz by identifier.
     *
     * @param id quiz identifier
     * @return quiz
     */
    public Quiz findById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
    }
}
