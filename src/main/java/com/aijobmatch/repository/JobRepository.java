package com.aijobmatch.repository;

import com.aijobmatch.model.Job;
import com.aijobmatch.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    List<Job> findByStatus(JobStatus status);
    
    List<Job> findByStatusIn(List<JobStatus> statuses);
    
    @Query("SELECT j FROM Job j WHERE j.status = :status " +
           "AND (LOWER(j.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(j.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(j.company) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Job> searchByQuery(@Param("query") String query, @Param("status") JobStatus status);
    
    @Query("SELECT j FROM Job j WHERE j.status = :status " +
           "AND j.location = :location")
    List<Job> findByStatusAndLocation(@Param("status") JobStatus status, @Param("location") String location);
}
