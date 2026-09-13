package com.example.lld_practice_platform.service;

import com.example.lld_practice_platform.model.Problem;
import com.example.lld_practice_platform.model.Submission;
import com.example.lld_practice_platform.model.EvaluationResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LLMEvaluator implements Evaluator {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EvaluationResult evaluate(Submission submission, Problem problem) {
        EvaluationResult result = new EvaluationResult();

        try {
            String prompt = buildPrompt(submission, problem);
            String rawResponse = callGemini(prompt);
            String jsonText = extractJson(rawResponse);

            JsonNode feedback = objectMapper.readTree(jsonText);

            result.setStrengths(feedback.get("strengths").toString());
            result.setIssues(feedback.get("issues").toString());
            result.setSuggestions(feedback.get("suggestions").toString());
            result.setVerdict(feedback.get("verdict").asText());

        } catch (Exception e) {
            result.setVerdict("evaluation_failed");
        }
        return result;
    }

    private String buildPrompt(Submission submission, Problem problem) {
        return """
            You are evaluating a learner's Low-Level Design (LLD) submission.

            PROBLEM:
            %s

            REFERENCE SOLUTION (use only as context for what a solid design looks like.
            Do NOT penalize the learner for differing from this if their approach is
            still valid — there can be more than one correct LLD solution):
            %s

            LEARNER'S SUBMISSION:
            Classes & Objects: %s
            Responsibilities: %s
            Interfaces & Abstractions: %s
            Relationships: %s
            Design Patterns: %s
            Extensibility notes: %s

            Evaluate this design for responsibility separation, appropriate abstraction,
            pattern usage (if any), and extensibility. Give specific, actionable feedback.

            Respond ONLY with valid JSON in exactly this format, no other text:
            {
              "strengths": ["...", "..."],
              "issues": ["...", "..."],
              "suggestions": ["...", "..."],
              "verdict": "solid" or "needs_improvement"
            }
            """.formatted(
                problem.getRequirements(),
                problem.getReferenceSolution(),
                submission.getClassesAndObjects(),
                submission.getResponsibilities(),
                submission.getInterfacesAndAbstractions(),
                submission.getRelationships(),
                submission.getDesignPatterns(),
                submission.getExtensibility()
        );
    }

    private String callGemini(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=" + apiKey;

        String requestBody = """
            {
              "contents": [{
                "parts": [{"text": %s}]
              }]
            }
            """.formatted(objectMapper.valueToTree(prompt).toString());

        return restClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);
    }

    private String extractJson(String rawResponse) throws Exception {
        JsonNode root = objectMapper.readTree(rawResponse);
        String text = root
                .path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text").asText();

        // strip markdown code fences if Gemini wraps the JSON in ```json ... ```
        return text.replaceAll("```json", "").replaceAll("```", "").trim();
    }
}