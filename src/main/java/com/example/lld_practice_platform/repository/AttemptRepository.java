package com.example.lld_practice_platform.repository;

import com.example.lld_practice_platform.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findBySessionId(String sessionId);
}