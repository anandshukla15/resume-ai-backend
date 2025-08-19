package com.resume.backend.resume_ai_backend.controller;


import com.resume.backend.resume_ai_backend.ResumeRequest;
import com.resume.backend.resume_ai_backend.service.ResumeService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/resume")
public class ResumeController {

     private ResumeService resumeService;

     public ResumeController(ResumeService resumeService){
         this.resumeService=resumeService;
     }


     @PostMapping("/generate")
     public ResponseEntity<JSONObject>getResumeData(
             @RequestBody ResumeRequest resumeRequest
     ) throws IOException {
     JSONObject jsonObject=resumeService.generateResumeResponse(resumeRequest.userDescription());
     return new ResponseEntity<>(jsonObject, HttpStatus.OK);
     }

}
