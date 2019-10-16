package com.example.eventsmgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.eventsmgmt.model.Role;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.repository.RoleRepository;
import com.example.eventsmgmt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * RegistrarService
 */
@Service
public class RegistrarService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventService eventService;

    public void addRegistrar(User user) {

        //Set registrar role
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName("REGISTRAR").orElseThrow(() -> new RuntimeException("Role not found")));
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
        Role registrar = roleRepository.findByName("REGISTRAR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Registrar Not Found By Id"));
        if(user.getRole().contains(registrar)) {
            return user;
        }
        throw new RuntimeException("Registrar Not Found!");
	}

	public List<User> getAllRegistrars() {
        Role registrar = roleRepository.findByName("REGISTRAR").orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles =  new HashSet<Role>();
        roles.add(registrar);
		return userRepository.findByRole(roles);
	}

	public User getByUsername(String username) {
        Role registrar = roleRepository.findByName("REGISTRAR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Registrar Not Found by username"));
        if(user.getRole().contains(registrar)) {
            return user;
        }
        throw new RuntimeException("Registrar Not Found!");
	}

	public void deleteById(int id) {
        Role registrar = roleRepository.findByName("REGISTRAR").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Registrar Not Found by username"));
        if(user.getRole().contains(registrar)) {
            userRepository.deleteById(id);
        }
        throw new RuntimeException("Registrar Not Found!");
    } 

    
    
}