package com.example.questionbank.api;

import com.example.questionbank.api.dto.CreateCompetencyRequest;
import com.example.questionbank.application.CompetencyService;
import com.example.questionbank.domain.model.Competency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST API for competency management.
 */
@RestController
@RequestMapping("/api/competencies")
public class CompetencyController {

    private final CompetencyService competencyService;

    /**
     * Creates a competency controller.
     *
     * @param competencyService competency service
     */
    public CompetencyController(CompetencyService competencyService) {
        this.competencyService = competencyService;
    }

    /**
     * Creates a competency.
     *
     * @param request competency data
     * @return created competency
     */
    @PostMapping
    public ResponseEntity<Competency> create(@RequestBody CreateCompetencyRequest request) {
        Competency competency = competencyService.create(request.name(), request.description());
        return ResponseEntity.created(URI.create("/api/competencies/" + competency.getId())).body(competency);
    }

    /**
     * Lists all competencies.
     *
     * @return competency list
     */
    @GetMapping
    public ResponseEntity<List<Competency>> findAll() {
        return ResponseEntity.ok(competencyService.findAll());
    }
}
