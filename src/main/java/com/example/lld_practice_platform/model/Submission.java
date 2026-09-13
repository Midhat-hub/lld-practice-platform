package com.example.lld_practice_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "submissions")
@Data
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "attempt_id")
    @JsonIgnore
    private Attempt attempt;

    @Column(columnDefinition = "TEXT")
    private String classesAndObjects;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String interfacesAndAbstractions;

    @Column(columnDefinition = "TEXT")
    private String relationships;

    @Column(columnDefinition = "TEXT")
    private String designPatterns; // optional

    @Column(columnDefinition = "TEXT")
    private String extensibility; // optional
}
