
# Banco de Preguntas Saber Pro

Proyecto académico desarrollado para el taller de **JPA** en el curso **Laboratorio de Ingeniería de Software II** de la Universidad del Cauca.

El sistema permite administrar usuarios, competencias, preguntas, opciones de respuesta, metadatos y cuestionarios para la preparación de simulacros tipo Saber Pro.

## Integrantes

- **Eddy Emmanuel Sanchez Obando**  
  Correo institucional: `eddysanchez@unicauca.edu.co`

- **Johan Santiago Lopez Ortega**  
  Correo institucional: `johanl@unicauca.edu.co`

## Propósito

El proyecto implementa una aplicación por capas con Spring Boot y Spring Data JPA. Su objetivo es demostrar cómo persistir un modelo de dominio limpio, sin acoplar las clases de negocio a anotaciones o dependencias de JPA.

La aplicación permite:

- Crear usuarios y competencias académicas.
- Crear preguntas de selección múltiple y de verdadero o falso.
- Asociar preguntas con autores, competencias y metadatos.
- Gestionar el ciclo de vida de una pregunta.
- Crear cuestionarios utilizando únicamente preguntas publicadas.
- Persistir la información en una base de datos H2 en memoria.

## Arquitectura

El proyecto utiliza una arquitectura en capas:

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Presentación | `api` | Expone los endpoints REST y recibe las solicitudes HTTP. |
| Aplicación | `application` | Coordina los casos de uso y aplica las reglas de negocio. |
| Dominio | `domain` | Contiene el modelo de negocio y los contratos de repositorio. |
| Infraestructura | `infrastructure.jpa` | Implementa la persistencia con JPA, Spring Data y mapeadores. |

El modelo de dominio no contiene anotaciones JPA. Las entidades de persistencia se encuentran separadas en el paquete `infrastructure.jpa.record` y se convierten a objetos de dominio mediante mapeadores.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- H2 Database
- JUnit 5

## Estructura del proyecto

```text
src/main/java/com/example/questionbank/
├── api/
│   ├── QuestionController.java
│   ├── QuizController.java
│   ├── UserController.java
│   ├── CompetencyController.java
│   ├── GlobalExceptionHandler.java
│   └── dto/
├── application/
│   ├── QuestionService.java
│   ├── QuizService.java
│   ├── UserService.java
│   └── CompetencyService.java
├── domain/
│   ├── model/
│   └── repository/
└── infrastructure/
    └── jpa/
        ├── record/
        ├── repository/
        ├── mapper/
        └── UserRepositoryJpa.java
```

## Modelo de dominio

El sistema incluye las siguientes clases principales:

- `User`: representa a un usuario del sistema.
- `Competency`: representa una competencia académica.
- `Question`: clase abstracta que representa una pregunta.
- `MultipleChoiceQuestion`: pregunta de selección múltiple.
- `TrueFalseQuestion`: pregunta de verdadero o falso.
- `AnswerOption`: opción de respuesta de una pregunta de selección múltiple.
- `QuestionMetadata`: metadatos complementarios de una pregunta.
- `Quiz`: cuestionario compuesto por preguntas publicadas.

### Ciclo de vida de una pregunta

```text
DRAFT -> PENDING_REVIEW -> IN_REVIEW -> APPROVED -> PUBLISHED -> ARCHIVED
```

Una pregunta puede ser rechazada durante la revisión y regresar al estado `DRAFT` para su corrección. Solo las preguntas en estado `PUBLISHED` pueden incluirse en un cuestionario.

## Relaciones JPA

El proyecto implementa los cuatro tipos de relaciones solicitados:

| Relación | Entidades | Tipo |
|---|---|---|
| Pregunta y metadatos | `QuestionRecord` – `QuestionMetadataRecord` | `@OneToOne` |
| Pregunta y opciones | `MultipleChoiceQuestionRecord` – `AnswerOptionRecord` | `@OneToMany` |
| Pregunta y autor | `QuestionRecord` – `UserRecord` | `@ManyToOne` |
| Pregunta y competencias | `QuestionRecord` – `CompetencyRecord` | `@ManyToMany` |
| Cuestionario y preguntas | `QuizRecord` – `QuestionRecord` | `@ManyToMany` |

La herencia de preguntas utiliza la estrategia `JOINED`, con `QuestionRecord` como tabla base y tablas especializadas para cada tipo de pregunta.

## Requisitos

- Java 17 o superior.
- Maven 3.8 o superior.

## Ejecución

Clona el repositorio:

```bash
git clone https://github.com/TU_USUARIO/NOMBRE_DEL_REPOSITORIO.git
cd NOMBRE_DEL_REPOSITORIO
```

Ejecuta la aplicación:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en:

```text
http://localhost:8080
```

## Consola H2

La consola de H2 está disponible en:

```text
http://localhost:8080/h2-console
```

Datos de conexión:

```text
JDBC URL: jdbc:h2:mem:questionbankdb
User Name: sa
Password:
```

## Pruebas

Para ejecutar las pruebas de integración:

```bash
mvn test
```

Las pruebas validan la persistencia de preguntas, relaciones, herencia, ciclo de vida y reglas de negocio.

## Ejemplos de uso

### Crear un usuario

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Eddy Sanchez","email":"eddy@example.com"}'
```

### Crear una competencia

```bash
curl -X POST http://localhost:8080/api/competencies \
  -H "Content-Type: application/json" \
  -d '{"name":"Software Design","description":"Diseño y evaluación de soluciones de software"}'
```

### Crear una pregunta de selección múltiple

```bash
curl -X POST http://localhost:8080/api/questions \
  -H "Content-Type: application/json" \
  -d '{
    "type": "MULTIPLE_CHOICE",
    "statement": "¿Qué capa coordina los casos de uso?",
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

### Publicar una pregunta

```bash
curl -X POST http://localhost:8080/api/questions/1/review
curl -X POST http://localhost:8080/api/questions/1/approve
curl -X POST http://localhost:8080/api/questions/1/publish
```

### Crear un cuestionario

```bash
curl -X POST http://localhost:8080/api/quizzes \
  -H "Content-Type: application/json" \
  -d '{"title":"Quiz de Ingeniería de Software","questionIds":[1]}'
```

## Universidad

**Universidad del Cauca**  
Facultad de Ingeniería Electrónica y Telecomunicaciones  
Departamento de Sistemas  
Programa de Ingeniería de Sistemas  

Popayán, Cauca  
2026
