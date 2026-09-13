package com.example.lld_practice_platform.repository;

import com.example.lld_practice_platform.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}