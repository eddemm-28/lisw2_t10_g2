package com.example.questionbank.application;

import com.example.questionbank.domain.model.*;
import com.example.questionbank.domain.repository.CompetencyRepository;
import com.example.questionbank.domain.repository.QuestionRepository;
import com.example.questionbank.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates question use cases and business rules.
 */
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final CompetencyRepository competencyRepository;

    /**
     * Creates a question service.
     *
     * @param questionRepository question persistence contract
     * @param userRepository user persistence contract
     * @param competencyRepository competency persistence contract
     */
    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           CompetencyRepository competencyRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.competencyRepository = competencyRepository;
    }

    /**
     * Creates a question in DRAFT status.
     *
     * @param type question type: MULTIPLE_CHOICE or TRUE_FALSE
     * @param statement question statement
     * @param difficulty question difficulty
     * @param authorId author identifier
     * @param competencyIds competencies associated with the question
     * @param options answer options for multiple choice questions
     * @param correctAnswer correct answer for multiple choice questions
     * @param expectedAnswer expected value for true/false questions
     * @return created question
     */
    @Transactional
    public Question createQuestion(String type, String statement, String difficulty, Long authorId,
                                   List<Long> competencyIds, List<AnswerOption> options,
                                   String correctAnswer, Boolean expectedAnswer) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        List<Competency> competencies = competencyRepository.findAllById(competencyIds == null ? List.of() : competencyIds);
        if (competencyIds != null && !competencyIds.isEmpty() && competencies.size() != competencyIds.size()) {
            throw new IllegalArgumentException("One or more competencies do not exist");
        }

        Question question;
        if ("MULTIPLE_CHOICE".equalsIgnoreCase(type)) {
            validateMultipleChoice(options, correctAnswer);
            question = new MultipleChoiceQuestion(null, statement, difficulty, QuestionStatus.DRAFT,
                    author, options, correctAnswer);
        } else if ("TRUE_FALSE".equalsIgnoreCase(type)) {
            if (expectedAnswer == null) {
                throw new IllegalArgumentException("True/false questions require an expected answer");
            }
            question = new TrueFalseQuestion(null, statement, difficulty, QuestionStatus.DRAFT,
                    author, expectedAnswer);
        } else {
            throw new IllegalArgumentException("Unsupported question type");
        }

        question.setMetadata(new QuestionMetadata(null, "UNDERSTAND", 2, null));
        question.setCompetencies(competencies);
        return questionRepository.save(question);
    }

    private void validateMultipleChoice(List<AnswerOption> options, String correctAnswer) {
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("A multiple choice question requires at least two options");
        }
        long correctCount = options.stream().filter(AnswerOption::isCorrect).count();
        if (correctCount != 1) {
            throw new IllegalArgumentException("A multiple choice question must have exactly one correct option");
        }
        if (correctAnswer == null || correctAnswer.isBlank()) {
            throw new IllegalArgumentException("A multiple choice question requires a correct answer");
        }
    }

    /**
     * Finds a question by identifier.
     *
     * @param id question identifier
     * @return optional question
     */
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    /**
     * Lists all questions.
     *
     * @return all questions
     */
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    /**
     * Lists questions filtered by status.
     *
     * @param status required status
     * @return matching questions
     */
    public List<Question> findByStatus(QuestionStatus status) {
        return questionRepository.findByStatus(status);
    }

    /**
     * Lists published questions associated with a competency.
     *
     * @param competencyId competency identifier
     * @return published questions
     */
    public List<Question> findPublishedByCompetency(Long competencyId) {
        return questionRepository.findPublishedByCompetencyId(competencyId);
    }

    /**
     * Sends a DRAFT question to review.
     *
     * @param id question identifier
     * @return updated question
     */
    @Transactional
    public Question sendToReview(Long id) {
        Question question = findById(id);
        if (question.getStatus() != QuestionStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT questions can be sent to review");
        }
        question.setStatus(QuestionStatus.PENDING_REVIEW);
        return questionRepository.save(question);
    }

    /**
     * Approves a question that is pending review.
     *
     * @param id question identifier
     * @return updated question
     */
    @Transactional
    public Question approve(Long id) {
        Question question = findById(id);
        if (question.getStatus() != QuestionStatus.PENDING_REVIEW && question.getStatus() != QuestionStatus.IN_REVIEW) {
            throw new IllegalStateException("Only PENDING_REVIEW or IN_REVIEW questions can be approved");
        }
        question.setStatus(QuestionStatus.APPROVED);
        if (question.getMetadata() != null) {
            question.getMetadata().setLastReviewDate(LocalDateTime.now());
        }
        return questionRepository.save(question);
    }

    /**
     * Publishes an approved question.
     *
     * @param id question identifier
     * @return updated question
     */
    @Transactional
    public Question publish(Long id) {
        Question question = findById(id);
        if (question.getStatus() != QuestionStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED questions can be published");
        }
        question.setStatus(QuestionStatus.PUBLISHED);
        return questionRepository.save(question);
    }

    /**
     * Rejects a question and returns it to DRAFT status.
     *
     * @param id question identifier
     * @return updated question
     */
    @Transactional
    public Question reject(Long id) {
        Question question = findById(id);
        if (question.getStatus() != QuestionStatus.PENDING_REVIEW && question.getStatus() != QuestionStatus.IN_REVIEW) {
            throw new IllegalStateException("Only questions under review can be rejected");
        }
        question.setStatus(QuestionStatus.DRAFT);
        return questionRepository.save(question);
    }
}
