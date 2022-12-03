package com.event.organizer.api.controller;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.dto.EventRequestDto;
import com.event.organizer.api.service.EventService;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
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
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> findAll() {
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

    private List<Event> getAllEvents(List<EventRequestDto> eventRequestDtos) {
        List<Event> allEvents = new ArrayList<>();
        eventRequestDtos.forEach(event -> {
            Event newEvent = new Event();
            newEvent.setId(event.getId());
            newEvent.setName(event.getName());
            newEvent.setTime(event.getLocalDateTime());
            if (Event.STATUSES.contains(event.getStatus())) {
                newEvent.setStatus(event.getStatus());
            } else {
                newEvent.setStatus(Event.NONE_STATUS);
            }
            if (!allEvents.contains(newEvent)) {
                allEvents.add(newEvent);
            }
        });
        return allEvents;
    }

    private List<Event> getAllEventsFA() {
        return findAll();
    }

    private void changeEventStatus(long EventId, String status) {
        List<Event> allEvents = findAll();
        allEvents.stream().filter(event -> event.getId() == EventId).forEach(event -> {
            if (Event.STATUSES.contains(status)) {
                event.setStatus(status);
            }
        });
    }
}
