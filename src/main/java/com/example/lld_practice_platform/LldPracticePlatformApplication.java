package com.example.lld_practice_platform;

import com.example.lld_practice_platform.model.Problem;
import com.example.lld_practice_platform.model.Submission;
import com.example.lld_practice_platform.service.LLMEvaluator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LldPracticePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LldPracticePlatformApplication.class, args);
	}

}