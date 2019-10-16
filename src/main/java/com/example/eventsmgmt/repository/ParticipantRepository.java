package com.example.eventsmgmt.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.model.Participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ParticipantRepository
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    Optional<Participant> findByEmail(String name);
    
    Optional<Participant> findById(int id);

    boolean existsByEmail(String email);
    
    List<Participant> findByEvents(Set<Event> events);
    
}