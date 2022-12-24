package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Comment;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.Image;
import com.event.organizer.api.repository.EventRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    private final AppUserService appUserService;

    public List<Event> findAll(String currentUserEmail) {
        AppUser currentUser = appUserService.findUserByEmail(currentUserEmail).get();

        List<AppUser> currentUserBlockList = currentUser.getBlockedUsers();

        List<Event> allEvents = eventRepository.findAll();

        List<Event> userEventsFeed = new ArrayList<Event>();

        for (Event event : allEvents) {
            AppUser eventCreator = event.getCreator();

            for (AppUser blockedUser : currentUserBlockList) {
                if(!eventCreator.getEmail().equals(blockedUser.getEmail())) {
                    userEventsFeed.add(event);
                }
            }
        }

        return userEventsFeed;
    }

    public Event getEventById(long eventId, String currentUserEmail) {
        AppUser currentUser = appUserService.findUserByEmail(currentUserEmail).get();

        Event event = eventRepository.findById(eventId).get();

        List<Comment> eventCommentsList = new ArrayList<Comment>();

        List<AppUser> blockedUsers = currentUser.getBlockedUsers();               

        for(Comment comment : event.getComments()) {
            String commentCreator = comment.getOwnersUsername();

            for(AppUser blockedUser : blockedUsers) {
                if(!blockedUser.getEmail().equals(commentCreator)) {
                    eventCommentsList.add(comment);
                }
            }
        }

        event.setComments(eventCommentsList);

        return event;
    }

    public Event saveEvent(Event event, String username) throws EventOrganizerException {
        if (event.getId() != null && eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event already exist");
        }

        AppUser creator = (AppUser) appUserService.loadUserByUsername(username);
        event.setCreator(creator);
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(creator);
        event.setAppUsers(appUsers);
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

    public void addComment(String comment, Long eventId, String username) throws EventOrganizerException  {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }
        Event event = eventRepository.findById(eventId).get();
        var allComments = event.getComments();

        Comment commentModel = new Comment();
        commentModel.setContent(comment);
        commentModel.setOwnersUsername(username);

        allComments.add(commentModel);
        event.setComments(allComments);

        eventRepository.save(event);
    }

    public void addImage(String image, Long eventId, String username) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }

        Event event = eventRepository.findById(eventId).get();
        var allImages = event.getImages();

        Image imageModel = new Image();
        imageModel.setUrl(image);
        imageModel.setOwnerUsername(username);

        allImages.add(imageModel);
        event.setImages(allImages);

        eventRepository.save(event);
    }
}
