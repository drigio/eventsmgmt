package com.example.eventsmgmt.repository;

import com.example.eventsmgmt.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * EventRepository
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    boolean existsByName(String name);
}