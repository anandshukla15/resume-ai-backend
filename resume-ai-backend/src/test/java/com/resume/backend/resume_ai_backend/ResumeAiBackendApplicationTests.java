package com.resume.backend.resume_ai_backend;

import com.resume.backend.resume_ai_backend.service.ResumeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ResumeAiBackendApplicationTests {

	@Autowired
	private ResumeService resumeService;

	@Test
	void contextLoads() throws IOException {

		resumeService.generateResumeResponse("i am anand with 4 year of java experience");
	}

}

