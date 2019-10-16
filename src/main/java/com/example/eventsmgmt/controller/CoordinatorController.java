package com.example.eventsmgmt.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.model.payload.ApiResponse;
import com.example.eventsmgmt.service.CoordinatorService;
import com.example.eventsmgmt.service.RegistrarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CoordinatorController
 */
@RestController
@RequestMapping("api/coordinator")
public class CoordinatorController {


    @Autowired
    CoordinatorService coordinatorService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RegistrarService registrarService;
    
    /*
        Registrar Related 
    */

    @PostMapping(value = "/registrars/add")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody User user) {

        if (registrarService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Username already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        if (registrarService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        User addUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEmail(),
                user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getMobile());
        registrarService.addRegistrar(addUser);

        return new ResponseEntity<Object>(new ApiResponse(true, "Registrar Added Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/registrars/{id}")
    public User getAdminById(@PathVariable("id") int id) {
        return registrarService.getById(id);
    }

    @GetMapping(value = "/registrars/username/{username}")
    public User getAdminByUsername(@PathVariable("username") String username) {
        return registrarService.getByUsername(username);
    }

    @GetMapping(value = "/registrars")
    public List<User> getRegistrars() {
        return registrarService.getAllRegistrars();
    }

    @DeleteMapping(value = "/registrars/{id}")
    public ResponseEntity<?> deleteCoordinator(@PathVariable("id") int id) {
        if (!registrarService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        registrarService.deleteById(id);
        return new ResponseEntity<Object>(new ApiResponse(true, "Registrar Deleted Successfully"), HttpStatus.OK);
    }

}