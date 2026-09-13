package com.example.lld_practice_platform.controller;

import com.example.lld_practice_platform.dto.ProblemPublicDTO;
import com.example.lld_practice_platform.repository.ProblemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/problems")
@CrossOrigin(origins = "*")
public class ProblemController {

    private final ProblemRepository problemRepository;

    public ProblemController(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @GetMapping
    public List<ProblemPublicDTO> getAllProblems() {
        return problemRepository.findAll().stream()
                .map(ProblemPublicDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProblemPublicDTO getProblem(@PathVariable Long id) {
        return ProblemPublicDTO.from(
                problemRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"))
        );
    }
}