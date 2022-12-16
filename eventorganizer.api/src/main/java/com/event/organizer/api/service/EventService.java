package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Comment;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.repository.EventRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository; //TODO:FIX - this creates errors in the EventsTests 

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) throws EventOrganizerException {
        if (event.getId() != null && eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event already exist");
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

    public void addComment(String comment, Long eventId) throws EventOrganizerException  {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }
        Event event = eventRepository.findById(eventId).get();
        var allComments = event.getComments();

        Comment commentModel = new Comment();
        commentModel.setContent(comment);
        allComments.add(commentModel);
        event.setComments(allComments);

        eventRepository.save(event);
    }

    public void userIsInterestedInEvent(String email, long eventId){
        AppUser currentUser = userRepository.findByEmail(email).get();
        Event currentEvent = eventRepository.findById(eventId).get();

        currentEvent.getInterestedUsers().add(currentUser);
        eventRepository.save(currentEvent);
    }
}
