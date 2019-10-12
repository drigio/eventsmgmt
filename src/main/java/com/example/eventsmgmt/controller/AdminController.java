package com.example.eventsmgmt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.model.payload.ApiResponse;
import com.example.eventsmgmt.security.UserPrincipal;
import com.example.eventsmgmt.service.AdminService;
import com.example.eventsmgmt.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AdminController
 */
@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventService eventService;

    /*
        Admin Related API
    */

    @PostMapping(value = "/admins/add")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody User user) {

        if (adminService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Username already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        if (adminService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        User addUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEmail(),
                user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getMobile());
        adminService.addAdmin(addUser);

        return new ResponseEntity<Object>(new ApiResponse(true, "Admin Added Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/admins/{id}")
    public User getAdminById(@PathVariable("id") int id) {
        return adminService.getById(id);
    }

    @GetMapping(value = "/admins/username/{username}")
    public User getAdminByUsername(@PathVariable("username") String username) {
        return adminService.getByUsername(username);
    }

    @GetMapping(value = "/admins")
    public List<User> getAdmins() {
        return adminService.getAllAdmins();
    }

    @DeleteMapping(value = "/admins/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("id") int id) {
        if (!adminService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        adminService.deleteById(id);
        return new ResponseEntity<Object>(new ApiResponse(true, "Admin Deleted Successfully"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/admins/{id}")
    public ResponseEntity<?> updateAdminById(@PathVariable("id") int id, @Valid @RequestBody User user) {
        if (!adminService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        adminService.updateAdminById(id, user);
        return new ResponseEntity<Object>(new ApiResponse(true, "Admin Updated Successfully"), HttpStatus.BAD_REQUEST);
    }

    /*
        Events Related API
    */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/events/add")
    public ResponseEntity<?> addEvent(@Valid @RequestBody Event event) {
        if (eventService.existsByName(event.getName())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Event name already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User currentUser = adminService.getByUsername(userPrincipal.getUsername());

        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());
        
        Event addEvent = new Event(
            event.getName(), 
            event.getDesc(),
            currTimestamp,
            currentUser,
            currTimestamp,
            currentUser
            );
        eventService.addEvent(addEvent);
        
        return new ResponseEntity<Object>(new ApiResponse(true, "Event Added Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/events/{id}")
    public Event getEventById(@PathVariable("id") int id) {
        return eventService.getById(id);
    }

    @GetMapping(value = "/events")
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }

    @DeleteMapping(value = "/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") int id) {
        if (!eventService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Event Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        eventService.deleteById(id);
        return new ResponseEntity<Object>(new ApiResponse(true, "Event Deleted Successfully"), HttpStatus.BAD_REQUEST);
    }

    
}