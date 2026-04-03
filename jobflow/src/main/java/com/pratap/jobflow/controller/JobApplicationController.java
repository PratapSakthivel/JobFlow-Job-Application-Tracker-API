package com.pratap.jobflow.controller;

import com.pratap.jobflow.dto.JobApplicationRequest;
import com.pratap.jobflow.dto.JobApplicationResponse;
import com.pratap.jobflow.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationResponse> addJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(
                jobApplicationService.addJob(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getAllJobs(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String company) {
        return ResponseEntity.ok(
                jobApplicationService.getAllJobs(userDetails.getUsername(), status, company));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getJobById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        return ResponseEntity.ok(
                jobApplicationService.getJobById(userDetails.getUsername(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> updateJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(
                jobApplicationService.updateJob(userDetails.getUsername(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        jobApplicationService.deleteJob(userDetails.getUsername(), id);
        return ResponseEntity.ok("Job application deleted successfully");
    }
}