package com.example.questionbank.infrastructure.jpa.mapper;

import com.example.questionbank.domain.model.Quiz;
import com.example.questionbank.infrastructure.jpa.record.QuestionRecord;
import com.example.questionbank.infrastructure.jpa.record.QuizRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps between the Quiz domain model and its JPA representation.
 */
@Component
public class QuizMapper {

    private final QuestionMapper questionMapper;

    /**
     * Creates a quiz mapper.
     *
     * @param questionMapper mapper for questions
     */
    public QuizMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    /**
     * Converts a domain quiz to a JPA record.
     *
     * @param domain domain quiz
     * @return JPA quiz record
     */
    public QuizRecord toRecord(Quiz domain) {
        QuizRecord record = new QuizRecord();
        record.setId(domain.getId());
        record.setTitle(domain.getTitle());

        List<QuestionRecord> questions = new ArrayList<>();
        for (var question : domain.getQuestions()) {
            questions.add(questionMapper.toRecord(question));
        }
        record.setQuestions(questions);
        return record;
    }

    /**
     * Converts a JPA quiz record to a domain quiz.
     *
     * @param record JPA quiz record
     * @return domain quiz
     */
    public Quiz toDomain(QuizRecord record) {
        List<com.example.questionbank.domain.model.Question> questions = new ArrayList<>();
        for (QuestionRecord questionRecord : record.getQuestions()) {
            questions.add(questionMapper.toDomain(questionRecord));
        }
        return new Quiz(record.getId(), record.getTitle(), questions);
    }
}
