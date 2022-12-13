package com.event.organizer.api.service;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.dto.EventRequestDto;
import com.event.organizer.api.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) throws EventOrganizerException {
        if (event.getId() != null && eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event already exist");
        }
        if (event.getTime().isBefore(LocalDateTime.now())) {
            throw new EventOrganizerException("Event can not be created prior to current date");
        }
        // TODO: Now its possible to create event with nulls for everything. 
        // Create validation for Name, localDateTime, status.

        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) throws EventOrganizerException {
        if (!eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event does not exist");
        }
        return eventRepository.save(event);
    }

    public void deleteEvent(Event event) throws EventOrganizerException {
        if (!eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event does not exist");
        }
        eventRepository.delete(event);
    }

    public List<Event> getAllPendingEvents() {
        return this.findAll()
                .stream()
                .filter(event -> Objects.equals(event.getStatus(), Event.NONE_STATUS))
                .collect(Collectors.toList());
    }

}



