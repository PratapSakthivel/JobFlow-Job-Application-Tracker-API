package com.pratap.jobflow.repository;

import com.pratap.jobflow.entity.JobApplication;
import com.pratap.jobflow.entity.Status;
import com.pratap.jobflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUser(User user);

    List<JobApplication> findByUserAndStatus(User user, Status status);

    List<JobApplication> findByUserAndCompanyNameContainingIgnoreCase(User user, String companyName);

    Optional<JobApplication> findByIdAndUser(Long id, User user);

    long countByUserAndStatus(User user, Status status);
}