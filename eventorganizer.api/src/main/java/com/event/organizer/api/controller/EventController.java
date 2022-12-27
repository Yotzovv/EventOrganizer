package com.event.organizer.api.controller;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.dto.EventRequestDto;
import com.event.organizer.api.model.dto.CommentRequestDto;
import com.event.organizer.api.model.dto.FeedbackRequestDto;
import com.event.organizer.api.model.dto.ImageRequestDto;
import com.event.organizer.api.service.EventService;

import java.security.Principal;
import java.time.LocalDateTime;
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
        return eventService.findAll(principal.getName());
    }

    @PostMapping
    public Event createEvent(@RequestBody EventRequestDto eventRequestDto, Principal principal)
            throws EventOrganizerException {
        Event event = getEventModel(eventRequestDto);
        return eventService.saveEvent(event, principal.getName());
    }

    @PutMapping
    public Event updateEvent(@RequestBody EventRequestDto eventRequestDto, Principal princpal) throws EventOrganizerException {
        Event event = getEventModel(eventRequestDto);
        return eventService.updateEvent(event, princpal.getName());
    }

    @DeleteMapping
    public String deleteEvent(@RequestBody EventRequestDto eventRequestDto) throws EventOrganizerException {
        Event event = getEventModel(eventRequestDto);
        eventService.deleteEvent(event);
        return "deleted";
    }

    @PostMapping("/addComment")
    public void addComment(@RequestBody CommentRequestDto request, Principal principal) throws EventOrganizerException {
        eventService.addComment(request.getComment(), request.getEventId(), principal.getName());
    }

    @PostMapping("/addImage")
    public void addImage(@RequestBody ImageRequestDto request, Principal principal) throws  EventOrganizerException {
        eventService.addImage(request.getUrl(), request.getEventId(), principal.getName());
    }

    @PostMapping("/addFeedback")
    public void addFeedback(@RequestBody FeedbackRequestDto request, Principal principal) throws EventOrganizerException {
        request.setRating(request.getRating());
        eventService.addFeedback(request.getRating(), request.getComment(), request.getEventId(), principal.getName());
    }

    @GetMapping("/getEventById")
    public Event getEventById(long eventId, Principal principal) {
        return eventService.getEventById(eventId, principal.getName());
    }

    private Event getEventModel(EventRequestDto eventRequestDto) throws EventOrganizerException {
        Event event = new Event();
        event.setId(eventRequestDto.getId());
        event.setName(eventRequestDto.getName());

        validateEndDateAfterStartDate(eventRequestDto.getStartDate(), eventRequestDto.getEndDate());
        event.setStartDate(eventRequestDto.getStartDate());
        event.setEndDate(eventRequestDto.getEndDate());

        event.setDescription(eventRequestDto.getDescription());
        event.setStatus(eventRequestDto.getStatus());
        event.setLocation(eventRequestDto.getLocation());

        if (Event.STATUSES.contains(eventRequestDto.getStatus())) {
            event.setStatus(eventRequestDto.getStatus());
        } else {
            event.setStatus(Event.NONE_STATUS);
        }
        return event;
    }

    private void validateEndDateAfterStartDate(LocalDateTime startDate, LocalDateTime endDate)
            throws EventOrganizerException {
        if (endDate.isBefore(startDate)) {
            throw new EventOrganizerException("Start date should be before end date");
        }
    }
}