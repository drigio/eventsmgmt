package com.example.eventsmgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.model.Role;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.repository.RoleRepository;
import com.example.eventsmgmt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService
 */
@Service
public class CoordinatorService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventService eventService;

    public void addCoordinator(User user) {

        //Set Coordinator role
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName("COORDINATOR").orElseThrow(() -> new RuntimeException("Role not found")));
        user.setRole(roles);     

        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public User getById(int id) {
        Role coordinator = roleRepository.findByName("COORDINATOR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Coordinator Not Found By Id"));
        if(user.getRole().contains(coordinator)) {
            return user;
        }
        throw new RuntimeException("Coordinator Not Found!");
	}

	public List<User> getAllCoordinators() {
        Role coordinator = roleRepository.findByName("COORDINATOR").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles =  new HashSet<Role>();
        roles.add(coordinator);
		return userRepository.findByRole(roles);
	}

	public User getByUsername(String username) {
        Role coordinator = roleRepository.findByName("COORDINATOR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Coordinator Not Found by username"));
        if(user.getRole().contains(coordinator)) {
            return user;
        }
        throw new RuntimeException("Coordinator Not Found!");
	}

	public void deleteById(int id) {
        Role coordinator = roleRepository.findByName("COORDINATOR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Coordinator Not Found By Id"));
        if(user.getRole().contains(coordinator)) {
            userRepository.deleteById(id);
        }
        throw new RuntimeException("Coordinator Not Found!");
	}

	public void updateEventForCoordinator(int coordinatorId, int eventId) {
        Role coordinator = roleRepository.findByName("COORDINATOR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findById(coordinatorId).orElseThrow(() -> new RuntimeException("Coordinator Not Found By Id"));
        if(user.getRole().contains(coordinator)) {
            Event event = eventService.getById(eventId);
            user.setEvent(event);
            userRepository.save(user);
            return;
        }
        throw new RuntimeException("Coordinator Not Found!");
	}
}