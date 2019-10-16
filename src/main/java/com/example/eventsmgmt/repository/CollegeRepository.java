package com.example.eventsmgmt.repository;

import com.example.eventsmgmt.model.College;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CollegeRepository
 */
@Repository
public interface CollegeRepository extends JpaRepository<College, Integer> {

    
}