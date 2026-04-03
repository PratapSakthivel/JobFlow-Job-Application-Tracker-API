package com.pratap.jobflow.service;

import com.pratap.jobflow.dto.JobApplicationRequest;
import com.pratap.jobflow.dto.JobApplicationResponse;
import com.pratap.jobflow.entity.JobApplication;
import com.pratap.jobflow.entity.Status;
import com.pratap.jobflow.entity.User;
import com.pratap.jobflow.repository.JobApplicationRepository;
import com.pratap.jobflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private JobApplicationResponse toResponse(JobApplication job) {
        return JobApplicationResponse.builder()
                .id(job.getId())
                .companyName(job.getCompanyName())
                .jobRole(job.getJobRole())
                .jdLink(job.getJdLink())
                .appliedDate(job.getAppliedDate())
                .status(job.getStatus())
                .notes(job.getNotes())
                .updatedAt(job.getUpdatedAt())
                .build();
    }

    public JobApplicationResponse addJob(String email, JobApplicationRequest request) {
        User user = getUser(email);

        JobApplication job = JobApplication.builder()
                .user(user)
                .companyName(request.getCompanyName())
                .jobRole(request.getJobRole())
                .jdLink(request.getJdLink())
                .appliedDate(request.getAppliedDate())
                .notes(request.getNotes())
                .build();

        return toResponse(jobApplicationRepository.save(job));
    }

    public List<JobApplicationResponse> getAllJobs(String email, String status, String company) {
        User user = getUser(email);

        if (status != null) {
            return jobApplicationRepository
                    .findByUserAndStatus(user, Status.valueOf(status.toUpperCase()))
                    .stream().map(this::toResponse).collect(Collectors.toList());
        }

        if (company != null) {
            return jobApplicationRepository
                    .findByUserAndCompanyNameContainingIgnoreCase(user, company)
                    .stream().map(this::toResponse).collect(Collectors.toList());
        }

        return jobApplicationRepository.findByUser(user)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public JobApplicationResponse getJobById(String email, Long id) {
        User user = getUser(email);
        JobApplication job = jobApplicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Job application not found"));
        return toResponse(job);
    }

    public JobApplicationResponse updateJob(String email, Long id, JobApplicationRequest request) {
        User user = getUser(email);
        JobApplication job = jobApplicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Job application not found"));

        job.setCompanyName(request.getCompanyName());
        job.setJobRole(request.getJobRole());
        job.setJdLink(request.getJdLink());
        job.setAppliedDate(request.getAppliedDate());
        job.setNotes(request.getNotes());

        if (request.getStatus() != null) {
            job.setStatus(request.getStatus());
        }

        return toResponse(jobApplicationRepository.save(job));
    }

    public void deleteJob(String email, Long id) {
        User user = getUser(email);
        JobApplication job = jobApplicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Job application not found"));
        jobApplicationRepository.delete(job);
    }
}