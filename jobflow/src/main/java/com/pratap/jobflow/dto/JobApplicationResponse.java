package com.pratap.jobflow.dto;

import com.pratap.jobflow.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationResponse {

    private Long id;
    private String companyName;
    private String jobRole;
    private String jdLink;
    private LocalDate appliedDate;
    private Status status;
    private String notes;
    private LocalDateTime updatedAt;
}