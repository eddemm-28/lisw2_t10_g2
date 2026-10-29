package com.example.questionbank.infrastructure.jpa.mapper;

import com.example.questionbank.domain.model.*;
import com.example.questionbank.infrastructure.jpa.record.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps between question domain objects and JPA records.
 */
@Component
public class QuestionMapper {

    private final UserMapper userMapper;
    private final CompetencyMapper competencyMapper;

    /**
     * Creates a question mapper.
     *
     * @param userMapper mapper for users
     * @param competencyMapper mapper for competencies
     */
    public QuestionMapper(UserMapper userMapper, CompetencyMapper competencyMapper) {
        this.userMapper = userMapper;
        this.competencyMapper = competencyMapper;
    }

    /**
     * Converts a domain question to its corresponding JPA record.
     *
     * @param domain domain question
     * @return JPA question record
     */
    public QuestionRecord toRecord(Question domain) {
        if (domain instanceof MultipleChoiceQuestion mc) {
            return toMultipleChoiceRecord(mc);
        }
        if (domain instanceof TrueFalseQuestion tf) {
            return toTrueFalseRecord(tf);
        }
        throw new IllegalArgumentException("Unsupported question type: " + domain.getClass().getSimpleName());
    }

    private QuestionRecord toMultipleChoiceRecord(MultipleChoiceQuestion domain) {
        MultipleChoiceQuestionRecord record = new MultipleChoiceQuestionRecord();
        copyCommonData(domain, record);
        record.setCorrectAnswer(domain.getCorrectAnswer());

        List<AnswerOptionRecord> optionRecords = new ArrayList<>();
        for (AnswerOption option : domain.getOptions()) {
            AnswerOptionRecord optionRecord = new AnswerOptionRecord();
            optionRecord.setId(option.getId());
            optionRecord.setText(option.getText());
            optionRecord.setCorrect(option.isCorrect());
            optionRecord.setQuestion(record);
            optionRecords.add(optionRecord);
        }
        record.setOptions(optionRecords);
        return record;
    }

    private QuestionRecord toTrueFalseRecord(TrueFalseQuestion domain) {
        TrueFalseQuestionRecord record = new TrueFalseQuestionRecord();
        copyCommonData(domain, record);
        record.setExpectedAnswer(domain.isExpectedAnswer());
        return record;
    }

    private void copyCommonData(Question domain, QuestionRecord record) {
        record.setId(domain.getId());
        record.setStatement(domain.getStatement());
        record.setDifficulty(domain.getDifficulty());
        record.setStatus(domain.getStatus());
        record.setAuthor(userMapper.toRecord(domain.getAuthor()));

        if (domain.getMetadata() != null) {
            QuestionMetadataRecord metadata = new QuestionMetadataRecord();
            metadata.setId(domain.getMetadata().getId());
            metadata.setCognitiveLevel(domain.getMetadata().getCognitiveLevel());
            metadata.setEstimatedTimeMinutes(domain.getMetadata().getEstimatedTimeMinutes());
            metadata.setLastReviewDate(domain.getMetadata().getLastReviewDate());
            record.setMetadata(metadata);
        }

        List<CompetencyRecord> competencies = new ArrayList<>();
        for (Competency competency : domain.getCompetencies()) {
            competencies.add(competencyMapper.toRecord(competency));
        }
        record.setCompetencies(competencies);
    }

    /**
     * Converts a JPA question record to its corresponding domain object.
     *
     * @param record JPA question record
     * @return domain question
     */
    public Question toDomain(QuestionRecord record) {
        if (record instanceof MultipleChoiceQuestionRecord mc) {
            List<AnswerOption> options = new ArrayList<>();
            for (AnswerOptionRecord optionRecord : mc.getOptions()) {
                options.add(new AnswerOption(optionRecord.getId(), optionRecord.getText(), optionRecord.isCorrect()));
            }
            MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                    mc.getId(), mc.getStatement(), mc.getDifficulty(), mc.getStatus(),
                    userMapper.toDomain(mc.getAuthor()), options, mc.getCorrectAnswer());
            copyCommonDomainData(record, question);
            return question;
        }
        if (record instanceof TrueFalseQuestionRecord tf) {
            TrueFalseQuestion question = new TrueFalseQuestion(
                    tf.getId(), tf.getStatement(), tf.getDifficulty(), tf.getStatus(),
                    userMapper.toDomain(tf.getAuthor()), tf.isExpectedAnswer());
            copyCommonDomainData(record, question);
            return question;
        }
        throw new IllegalArgumentException("Unsupported record type: " + record.getClass().getSimpleName());
    }

    private void copyCommonDomainData(QuestionRecord record, Question domain) {
        if (record.getMetadata() != null) {
            domain.setMetadata(new QuestionMetadata(
                    record.getMetadata().getId(),
                    record.getMetadata().getCognitiveLevel(),
                    record.getMetadata().getEstimatedTimeMinutes(),
                    record.getMetadata().getLastReviewDate()));
        }
        List<Competency> competencies = new ArrayList<>();
        for (CompetencyRecord competencyRecord : record.getCompetencies()) {
            competencies.add(competencyMapper.toDomain(competencyRecord));
        }
        domain.setCompetencies(competencies);
    }
}
