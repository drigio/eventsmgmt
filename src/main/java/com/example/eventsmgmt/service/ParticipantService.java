package com.example.eventsmgmt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.model.Participant;
import com.example.eventsmgmt.repository.ParticipantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ParticipantService
 */
@Service
public class ParticipantService {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    EventService eventService;

    public boolean existsByEmail(String email) {
		return participantRepository.existsByEmail(email);
    }

	public void addParticipant(Participant participant) {
        participantRepository.save(participant);
	}

	public boolean existsById(int participantId) {
		return participantRepository.existsById(participantId);
	}

	public void attachEventForParticipant(int participantId, int eventId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow(() -> new RuntimeException("Participant not found"));
        Event event = eventService.getById(eventId);

        Set<Event> events = participant.getEvents();
        if(events.contains(event)) {
            throw new RuntimeException("Participant already registered for the event");
        } else {
            events.add(event);
            participant.setEvents(events);
            participantRepository.save(participant);
        }
	}

	public List<Participant> getAllParticipantsByEventId(int eventId) {
        Event event = eventService.getById(eventId);
        // Very Poor way of doing this!
        List<Participant> participants = participantRepository.findAll();
        List<Participant> eventParticipants = new ArrayList<>();
        for(Participant p : participants) {
            if(p.getEvents().contains(event)) {
                eventParticipants.add(p);
            }
        }

        return eventParticipants;
    }
    
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

}