package com.example.eventsmgmt.repository;

import java.util.Optional;

import com.example.eventsmgmt.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    
    Optional<User> findById(int id);

    boolean existsByUsername(String username);

	boolean existsByEmail(String email);
    
}