package com.example.eventsmgmt.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.example.eventsmgmt.model.Participant;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.model.payload.ApiResponse;
import com.example.eventsmgmt.security.UserPrincipal;
import com.example.eventsmgmt.service.AdminService;
import com.example.eventsmgmt.service.EventService;
import com.example.eventsmgmt.service.ParticipantService;
import com.example.eventsmgmt.service.RegistrarService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RegistrarController
 */
@RestController
@RequestMapping("api/registrar")
public class RegistrarController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    RegistrarService registrarService;

    @Autowired
    EventService eventService;

    @PreAuthorize("hasAuthority('REGISTRAR')")
    @PostMapping(value = "/participants/add")
    public ResponseEntity<?> addParticipant(@Valid @RequestBody Participant participant) {

        if (participantService.existsByEmail(participant.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User currentUser = registrarService.getByUsername(userPrincipal.getUsername());

        Date date = new Date();
        Timestamp currTimestamp = new Timestamp(date.getTime());

        Participant addParticipant = new Participant(
            participant.getFirstName(),
            participant.getMiddleName(),
            participant.getLastName(),
            participant.getEmail(),
            participant.getMobile(),
            currTimestamp,
            currTimestamp,
            currentUser,
            currentUser
        );
        participantService.addParticipant(addParticipant);

        return new ResponseEntity<Object>(new ApiResponse(true, "Participant Added Successfully"), HttpStatus.OK);
    }

    @PutMapping(value = "/participants/event")
    public ResponseEntity<?> attachEventForParticipant(@RequestBody String body) {

        JSONObject jsonObj = new JSONObject(body);
        int participantId = Integer.parseInt(jsonObj.getString("participantId"));
        int eventId = Integer.parseInt(jsonObj.getString("eventId"));

        if (!participantService.existsById(participantId)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        } else if (!eventService.existsById(eventId)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Event Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }

        participantService.attachEventForParticipant(participantId, eventId);

        return new ResponseEntity<Object>(new ApiResponse(true, "Event Attached to Participant Successfully"), HttpStatus.OK);
    }

    // Get all participants registered for a particular event
    @GetMapping("/participants/event/{id}")
    public List<Participant> getAllParticipantsByEventId(@PathVariable("id") int eventId) {
        if (!eventService.existsById(eventId)) {
            throw new RuntimeException("Event id doesn't exist");
        }
        return participantService.getAllParticipantsByEventId(eventId);
    }

    @GetMapping("/participants/all")
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/participants/sendmail")
    public void sendMail() {
        participantService.sendMail("drigiobalboa@gmail.com", "Drigio", "Balboa", "Webber");
    }

}