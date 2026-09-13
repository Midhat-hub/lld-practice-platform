package com.example.lld_practice_platform.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "problems")
@Data

public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    private String difficulty; // "Easy", "Medium", "Hard"

    @Column(columnDefinition = "TEXT")
    private String referenceSolution; // used as LLM context, not shown to learner
}