package com.example.lld_practice_platform.service;

import com.example.lld_practice_platform.model.Problem;
import com.example.lld_practice_platform.model.Submission;
import com.example.lld_practice_platform.model.EvaluationResult;

public interface Evaluator {
    EvaluationResult evaluate(Submission submission, Problem problem);
}