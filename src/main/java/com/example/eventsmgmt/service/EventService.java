package com.example.eventsmgmt.service;

import java.util.List;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EventService
 */
@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public boolean existsByName(String name) {
        return eventRepository.existsByName(name);
    }

    public boolean existsById(int id) {
        return eventRepository.existsById(id);
    }

	public Event getById(int id) {
		return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot Get Event By Id"));
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public void deleteById(int id) {
        eventRepository.deleteById(id);
	}
    
}