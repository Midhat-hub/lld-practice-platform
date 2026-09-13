package com.example.lld_practice_platform.controller;

import com.example.lld_practice_platform.model.Attempt;
import com.example.lld_practice_platform.model.Submission;
import com.example.lld_practice_platform.service.AttemptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
@CrossOrigin(origins = "*") // fine for dev; tighten before/at deployment
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping
    public Attempt startAttempt(@RequestBody Map<String, Object> body) {
        Long problemId = Long.valueOf(body.get("problemId").toString());
        String sessionId = body.get("sessionId").toString();
        return attemptService.startAttempt(problemId, sessionId);
    }

    @PostMapping("/{attemptId}/submit")
    public String submit(@PathVariable Long attemptId, @RequestBody Submission submission) {
        attemptService.submitAndEvaluate(attemptId, submission);
        return "submitted";
    }

    @GetMapping("/history")
    public List<Attempt> getHistory(@RequestParam String sessionId) {
        return attemptService.getHistory(sessionId);
    }
}