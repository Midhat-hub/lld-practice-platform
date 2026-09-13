package com.example.lld_practice_platform.service;

import com.example.lld_practice_platform.model.Problem;
import com.example.lld_practice_platform.model.Submission;
import com.example.lld_practice_platform.model.EvaluationResult;
import org.springframework.stereotype.Component;

@Component
public class DeterministicEvaluator implements Evaluator {

    @Override
    public EvaluationResult evaluate(Submission submission, Problem problem) {
        EvaluationResult result = new EvaluationResult();

        result.setHasMinimumClasses(
                countLines(submission.getClassesAndObjects()) >= 2
        );

        result.setHasResponsibilitiesFilled(
                isNotBlank(submission.getResponsibilities())
        );

        result.setHasInterfacesFilled(
                isNotBlank(submission.getInterfacesAndAbstractions())
        );

        result.setMentionsExpectedConcepts(
                isNotBlank(submission.getInterfacesAndAbstractions()) &&
                        !submission.getInterfacesAndAbstractions().toLowerCase().contains("none")
        );
        return result;
    }

    private boolean isNotBlank(String text) {
        return text != null && !text.trim().isEmpty();
    }

    private int countLines(String text) {
        if (isNotBlank(text)) {
            return text.trim().split("[\\r?\\n,]+").length;
        }
        return 0;
    }

    private boolean containsAnyKeyword(Submission submission) {
        String combined = (
                nullSafe(submission.getClassesAndObjects()) + " " +
                        nullSafe(submission.getResponsibilities()) + " " +
                        nullSafe(submission.getDesignPatterns())
        ).toLowerCase();

        String[] keywords = {"class", "interface", "responsibility", "pattern"};
        for (String keyword : keywords) {
            if (combined.contains(keyword)) return true;
        }
        return false;
    }

    private String nullSafe(String text) {
        return text == null ? "" : text;
    }
}