package com.example.lld_practice_platform.repository;

import com.example.lld_practice_platform.model.EvaluationResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationResultRepository extends JpaRepository<EvaluationResult, Long> {
}