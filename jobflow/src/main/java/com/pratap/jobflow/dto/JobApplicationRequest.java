package com.pratap.jobflow.dto;

import com.pratap.jobflow.entity.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class JobApplicationRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    private String jdLink;

    private LocalDate appliedDate;

    private Status status;

    private String notes;
}