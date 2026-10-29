# Question Bank - Saber Pro

Mini project developed for the **JPA Laboratory** of Software Engineering II.  
It implements a layered Spring Boot application to manage questions, competencies, users and quizzes for Saber Pro preparation.

## Purpose

The application allows:

- Creating users and academic competencies.
- Creating multiple choice and true/false questions.
- Associating questions with authors, metadata and competencies.
- Managing the question lifecycle: `DRAFT -> PENDING_REVIEW -> APPROVED -> PUBLISHED`.
- Creating quizzes using only published questions.
- Persisting data in an in-memory H2 database.

## Architecture

The project uses a layered architecture:

| Layer | Package | Responsibility |
|---|---|---|
| Presentation | `api` | Exposes REST endpoints and request DTOs |
| Application | `application` | Coordinates use cases and business rules |
| Domain | `domain.model` | Pure business model without JPA annotations |
| Repository contract | `domain.repository` | Technology-independent persistence interfaces |
| Infrastructure | `infrastructure.jpa` | JPA records, Spring Data repositories, mappers and JPA repository implementations |

The domain model never imports `jakarta.persistence`. Persistence concerns are isolated in `infrastructure.jpa`.

## JPA design

- **Inheritance:** `JOINED`; `QuestionRecord` is the base table and each specialized question has its own table.
- **One-to-One:** `QuestionRecord` and `QuestionMetadataRecord`.
- **One-to-Many:** `MultipleChoiceQuestionRecord` and `AnswerOptionRecord`.
- **Many-to-One:** `QuestionRecord` and `UserRecord` (author).
- **Many-to-Many:** `QuestionRecord` and `CompetencyRecord`, using the `question_competency` join table.
- **Many-to-Many:** `QuizRecord` and `QuestionRecord`, using the `quiz_question` join table.

## Requirements

- Java 17 or higher
- Maven 3.8 or higher

## Run the application

```bash
mvn spring-boot:run
```

The application starts at:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

Use these connection values:

```text
JDBC URL: jdbc:h2:mem:questionbankdb
User Name: sa
Password:
```

## Run tests

```bash
mvn test
```

## Example: create a user

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Eddy Sanchez","email":"eddy@example.com"}'
```

## Example: create a competency

```bash
curl -X POST http://localhost:8080/api/competencies \
  -H "Content-Type: application/json" \
  -d '{"name":"Software Design","description":"Design and evaluate software solutions"}'
```

## Example: create a multiple choice question

```bash
curl -X POST http://localhost:8080/api/questions \
  -H "Content-Type: application/json" \
  -d '{
    "type": "MULTIPLE_CHOICE",
    "statement": "Which layer coordinates use cases?",
    "difficulty": "MEDIUM",
    "authorId": 1,
    "competencyIds": [1],
    "options": [
      {"text": "API", "correct": false},
      {"text": "Application", "correct": true},
      {"text": "Infrastructure", "correct": false},
      {"text": "Domain", "correct": false}
    ],
    "correctAnswer": "Application"
  }'
```

## Example: publish a question

```bash
curl -X POST http://localhost:8080/api/questions/1/review
curl -X POST http://localhost:8080/api/questions/1/approve
curl -X POST http://localhost:8080/api/questions/1/publish
```

## Example: create a quiz

```bash
curl -X POST http://localhost:8080/api/quizzes \
  -H "Content-Type: application/json" \
  -d '{"title":"Software Engineering Quiz","questionIds":[1]}'
```

## Author

Eddy Emmanuel Sanchez Obando  
Universidad del Cauca
