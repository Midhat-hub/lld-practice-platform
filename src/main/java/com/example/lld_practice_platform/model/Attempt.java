package com.example.lld_practice_platform.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "attempts")
@Data
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "attempt", fetch = FetchType.EAGER)
    private EvaluationResult evaluationResult;

    public enum Status {
        PENDING, EVALUATED, FAILED
    }
}