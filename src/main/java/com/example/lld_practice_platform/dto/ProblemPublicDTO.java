package com.example.lld_practice_platform.dto;

import com.example.lld_practice_platform.model.Problem;
import lombok.Data;

@Data
public class ProblemPublicDTO {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String difficulty;

    public static ProblemPublicDTO from(Problem problem) {
        ProblemPublicDTO dto = new ProblemPublicDTO();
        dto.setId(problem.getId());
        dto.setTitle(problem.getTitle());
        dto.setDescription(problem.getDescription());
        dto.setRequirements(problem.getRequirements());
        dto.setDifficulty(problem.getDifficulty());
        return dto;
    }
}