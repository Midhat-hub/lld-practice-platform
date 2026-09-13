package com.example.lld_practice_platform.service;

import com.example.lld_practice_platform.model.*;
import com.example.lld_practice_platform.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final EvaluationResultRepository evaluationResultRepository;
    private final DeterministicEvaluator deterministicEvaluator;
    private final LLMEvaluator llmEvaluator;

    public AttemptService(AttemptRepository attemptRepository,
                          ProblemRepository problemRepository,
                          SubmissionRepository submissionRepository,
                          EvaluationResultRepository evaluationResultRepository,
                          DeterministicEvaluator deterministicEvaluator,
                          LLMEvaluator llmEvaluator) {
        this.attemptRepository = attemptRepository;
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.evaluationResultRepository = evaluationResultRepository;
        this.deterministicEvaluator = deterministicEvaluator;
        this.llmEvaluator = llmEvaluator;
    }

    public Attempt startAttempt(Long problemId, String sessionId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        Attempt attempt = new Attempt();
        attempt.setProblem(problem);
        attempt.setSessionId(sessionId);
        attempt.setStatus(Attempt.Status.PENDING);
        attempt.setCreatedAt(LocalDateTime.now());

        return attemptRepository.save(attempt);
    }

    public void submitAndEvaluate(Long attemptId, Submission submissionInput) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        submissionInput.setAttempt(attempt);
        Submission savedSubmission = submissionRepository.save(submissionInput);

        Problem problem = attempt.getProblem();

        EvaluationResult deterministicResult = deterministicEvaluator.evaluate(savedSubmission, problem);
        EvaluationResult llmResult = llmEvaluator.evaluate(savedSubmission, problem);

        // merge both into one result
        EvaluationResult combined = new EvaluationResult();
        combined.setAttempt(attempt);
        combined.setHasMinimumClasses(deterministicResult.isHasMinimumClasses());
        combined.setHasResponsibilitiesFilled(deterministicResult.isHasResponsibilitiesFilled());
        combined.setHasInterfacesFilled(deterministicResult.isHasInterfacesFilled());
        combined.setMentionsExpectedConcepts(deterministicResult.isMentionsExpectedConcepts());
        combined.setStrengths(llmResult.getStrengths());
        combined.setIssues(llmResult.getIssues());
        combined.setSuggestions(llmResult.getSuggestions());
        combined.setVerdict(llmResult.getVerdict());

        evaluationResultRepository.save(combined);

        if ("evaluation_failed".equals(llmResult.getVerdict())) {
            attempt.setStatus(Attempt.Status.FAILED);
        } else {
            attempt.setStatus(Attempt.Status.EVALUATED);
        }
        attemptRepository.save(attempt);
    }

    public List<Attempt> getHistory(String sessionId) {
        return attemptRepository.findBySessionId(sessionId);
    }
}