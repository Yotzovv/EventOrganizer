package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.dto.EventRequestDto;
import com.event.organizer.api.model.dto.CommentRequestDto;
import com.event.organizer.api.model.dto.FeedbackRequestDto;
import com.event.organizer.api.model.dto.ImageRequestDto;
import com.event.organizer.api.search.SearchCriteria;
import com.event.organizer.api.service.EventService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
@AllArgsConstructor
@CrossOrigin
public class EventController {

    private final EventService eventService;
    private final AppUserService appUserService;

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable long eventId, Principal principal) {
        return eventService.getEventById(eventId, principal.getName());
    }

    @PostMapping("/{eventId}/accept")
    public Event acceptEvent(@PathVariable long eventId, Principal principal) {
        return eventService.acceptEvent(eventId, principal.getName());
    }

    @PostMapping("/{eventId}/reject")
    public Event rejectEvent(@PathVariable long eventId, Principal principal) {
        return eventService.rejectEvent(eventId, principal.getName());
    }

    @GetMapping
    public Page<Event> findAll(
        @RequestParam(required = false, defaultValue = "6") int pageSize,
        @RequestParam(required = false, defaultValue = "0") int page,
        Principal principal
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<SearchCriteria> criterias = new ArrayList<>();

        return eventService.findAll(principal.getName(), pageable, criterias);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public List<Event> findAllPending() {
        return eventService.findAllPending();
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

    @PutMapping("/addInterested")
    public void userInterestedInEvent(@RequestBody Long eventId, Principal principal) throws EventOrganizerException {
        eventService.userIsInterestedInEvent(principal.getName(), eventId);
    }

    @PutMapping("/addGoing")
    public void userGoingToEvent(@RequestBody Long eventId, Principal principal) throws EventOrganizerException {
        eventService.userIsGoingToEvent(principal.getName(), eventId);
    }

    @GetMapping("/getThisWeeksEvents")
    public List<Event> getThisWeeksEvents() {
        return eventService.getThisWeeksEvents();
    }

    @GetMapping("/getThisMonthsEvents")
    public List<Event> getThisMonthsEvents() {
        return eventService.getThisMonthsEvents();
    }

    @GetMapping("getEventsByUserLocation")
    public List<Event> getEventsByUserLocation(Principal principal) {
        AppUser user = appUserService.findUserByEmail(principal.getName()).get();
        return eventService.getEventsByLocation(user.getLocation());
    }

    @GetMapping("getEventsByLocation")
    public List<Event> getEventsByLocation(String location) {
        return eventService.getEventsByLocation(location);
    }

    @GetMapping("/getInterestedUsers")
    public List<AppUser> getUsersInterestedInEvent(long eventId) throws EventOrganizerException {
        return eventService.getUsersInterestedInEvent(eventId);
    }

    @GetMapping("getGoingUsers")
    public List<AppUser> getUsersGoingToEvent(long eventId) throws EventOrganizerException {
        return eventService.getUsersGoingToEvent(eventId);
    }

    @GetMapping("getEventsByType")
    public List<Event> getEventsByType (String type) {
        return eventService.getEventsByType(type);
    }

    @GetMapping("getUserEvents")
    public List<Event> getHostingEvents(Principal principal) {
        return eventService.getHostingEvents(principal.getName());
    }

    @GetMapping("getEventsByRange")
    public List<Event> getEventsByDateRange(LocalDateTime startRange, LocalDateTime endRange) throws EventOrganizerException {
        validateEndDateAfterStartDate(startRange, endRange);
        return eventService.getEventsByDateRange(startRange, endRange);
    }

    @GetMapping("getEventsOfUser")
    public List<Event> getEventsOfUser(String username) {
        return eventService.getHostingEvents(username);
    }

    @GetMapping("getMyGoingToAndInterestedEvents")
    public Set<Event> getMyGoingToAndInterestedEvents(Principal principal)  {
        String username = principal.getName();

        List<Event> interestedEvents = eventService.getMyInterestedEvents(username);
        List<Event> goingEvents = eventService.getMyGoingToEvents(username);

        Set<Event> allEvents = new HashSet<Event>();
        allEvents.addAll(interestedEvents);
        allEvents.addAll(goingEvents);
        
        return allEvents;
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