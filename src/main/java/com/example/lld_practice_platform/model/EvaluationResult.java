package com.example.lld_practice_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "evaluation_results")
@Data
public class EvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "attempt_id")
    @JsonIgnore
    private Attempt attempt;

    private boolean hasMinimumClasses;
    private boolean hasResponsibilitiesFilled;
    private boolean hasInterfacesFilled;
    private boolean mentionsExpectedConcepts;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String issues;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    private String verdict;
}