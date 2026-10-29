package com.example.questionbank;

import com.example.questionbank.api.dto.*;
import com.example.questionbank.application.CompetencyService;
import com.example.questionbank.application.QuestionService;
import com.example.questionbank.application.QuizService;
import com.example.questionbank.application.UserService;
import com.example.questionbank.domain.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for persistence, inheritance, relationships and business rules.
 */
@SpringBootTest
class QuestionBankIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CompetencyService competencyService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    private Long createAuthor() {
        return userService.create("Eddy Sanchez", "eddy@example.com").getId();
    }

    private Long createCompetency() {
        return competencyService.create("Software Design", "Design quality").getId();
    }

    @Test
    void shouldPersistAndRecoverMultipleChoiceQuestionWithRelations() {
        Long authorId = createAuthor();
        Long competencyId = createCompetency();

        Question question = questionService.createQuestion(
                "MULTIPLE_CHOICE",
                "Which layer coordinates use cases?",
                "MEDIUM",
                authorId,
                List.of(competencyId),
                List.of(
                        new AnswerOption(null, "API", false),
                        new AnswerOption(null, "Application", true),
                        new AnswerOption(null, "Infrastructure", false),
                        new AnswerOption(null, "Domain", false)
                ),
                "Application",
                null
        );

        assertNotNull(question.getId());
        assertEquals(QuestionStatus.DRAFT, question.getStatus());
        assertEquals(authorId, question.getAuthor().getId());
        assertEquals(1, question.getCompetencies().size());
        assertEquals(4, ((MultipleChoiceQuestion) question).getOptions().size());
        assertNotNull(question.getMetadata());
    }

    @Test
    void shouldPersistAndRecoverTrueFalseQuestion() {
        Long authorId = createAuthor();
        Question question = questionService.createQuestion(
                "TRUE_FALSE", "Spring Data JPA is part of Spring Boot.", "EASY",
                authorId, List.of(), List.of(), null, true);

        Question recovered = questionService.findById(question.getId());
        assertInstanceOf(TrueFalseQuestion.class, recovered);
        assertTrue(((TrueFalseQuestion) recovered).isExpectedAnswer());
    }

    @Test
    void shouldApplyQuestionLifecycleAndAllowPublishedQuestionInQuiz() {
        Long authorId = createAuthor();
        Question question = questionService.createQuestion(
                "TRUE_FALSE", "H2 can run in memory.", "EASY",
                authorId, List.of(), List.of(), null, true);

        assertThrows(IllegalStateException.class, () -> quizService.createQuiz("Invalid quiz", List.of(question.getId())));

        questionService.sendToReview(question.getId());
        questionService.approve(question.getId());
        questionService.publish(question.getId());

        Quiz quiz = quizService.createQuiz("SO quiz", List.of(question.getId()));
        assertEquals(1, quiz.getQuestions().size());
        assertEquals(QuestionStatus.PUBLISHED, quiz.getQuestions().get(0).getStatus());
    }

    @Test
    void shouldRejectInvalidMultipleChoiceQuestion() {
        Long authorId = createAuthor();
        assertThrows(IllegalArgumentException.class, () -> questionService.createQuestion(
                "MULTIPLE_CHOICE", "Invalid question", "EASY", authorId, List.of(),
                List.of(new AnswerOption(null, "A", false)), "A", null));
    }
}
