package com.example.lld_practice_platform.repository;

import com.example.lld_practice_platform.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}