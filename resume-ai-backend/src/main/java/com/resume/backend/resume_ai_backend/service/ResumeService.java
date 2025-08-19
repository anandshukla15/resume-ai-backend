package com.resume.backend.resume_ai_backend.service;

import org.json.JSONObject;

import java.io.IOException;

public interface ResumeService {
    JSONObject generateResumeResponse(String userResumeDescription) throws IOException;
}
