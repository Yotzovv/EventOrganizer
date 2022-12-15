package com.event.organizer.api.controller;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.dto.EventRequestDto;
import com.event.organizer.api.service.EventService;

import java.security.Principal;
import java.util.List;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
@AllArgsConstructor
@CrossOrigin
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> findAll(Principal principal) {
        return eventService.findAll();
    }

    @PostMapping
    public Event createEvent(@RequestBody EventRequestDto eventRequestDto) throws EventOrganizerException {
        Event event = getEvent(eventRequestDto);
        return eventService.saveEvent(event);
    }

    @PutMapping
    public Event updateEvent(@RequestBody EventRequestDto eventRequestDto) throws EventOrganizerException {
        Event event = getEvent(eventRequestDto);
        return eventService.updateEvent(event);
    }

    @DeleteMapping
    public String deleteEvent(@RequestBody EventRequestDto eventRequestDto) throws EventOrganizerException {
        Event event = getEvent(eventRequestDto);
        eventService.deleteEvent(event);
        return "deleted";
    }

    @PostMapping
    public void addComment(String comment, Long eventId) throws EventOrganizerException {
        eventService.addComment(comment, eventId);
    }

    private Event getEvent(EventRequestDto eventRequestDto) {
        Event event = new Event();
        event.setId(eventRequestDto.getId());
        event.setName(eventRequestDto.getName());
        event.setTime(eventRequestDto.getLocalDateTime());
        if (Event.STATUSES.contains(eventRequestDto.getStatus())) {
            event.setStatus(eventRequestDto.getStatus());
        } else {
            event.setStatus(Event.NONE_STATUS);
        }
        return event;
    }
}
