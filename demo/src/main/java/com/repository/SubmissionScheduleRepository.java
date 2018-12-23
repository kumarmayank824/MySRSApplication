package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.SubmissionSchedule;

@Repository
public interface SubmissionScheduleRepository extends JpaRepository<SubmissionSchedule,Long> {
    
}
