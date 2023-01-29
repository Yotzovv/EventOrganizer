package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Comment;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.model.Feedback;
import com.event.organizer.api.model.Image;
import com.event.organizer.api.repository.EventRepository;
import com.event.organizer.api.search.BaseSpecification;
import com.event.organizer.api.search.Search;
import com.event.organizer.api.search.SearchCriteria;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    private final AppUserService appUserService;

    public List<Event> findAll(String currentUserEmail) {
        return eventRepository.findAll();
    }

    public Page<Event> findAll(String currentUserEmail, Pageable page, List<SearchCriteria> criterias) {
        AppUser currentUser = appUserService.findUserByEmail(currentUserEmail).get();
        List<AppUser> currentUserBlockList = currentUser.getBlockedUsers();

        SearchCriteria notPending = new SearchCriteria("status", "=", Event.ACCEPTED_STATUS);
        SearchCriteria notBlockedCreator = new SearchCriteria("creator", "!:", currentUserBlockList);
        criterias.add(notPending);
        criterias.add(notBlockedCreator);

        Search<Event> search = new Search<Event>(criterias);
        
        Page<Event> allEvents = eventRepository.findAll(search.getSpecificationList(), page);
        return allEvents;
    }

    
    public List<Event> findAllPending() {
        List<SearchCriteria> criterias = new ArrayList<>();
        SearchCriteria notPending = new SearchCriteria("status", "=", Event.PENDING_STATUS);
        criterias.add(notPending);
        Search<Event> search = new Search<Event>(criterias);
        List<Event> allEvents = eventRepository.findAll(search.getSpecificationList());
        return allEvents;
    }

    public Event getEventById(long eventId, String currentUserEmail) {
        AppUser currentUser = appUserService.findUserByEmail(currentUserEmail).get();

        Event event = eventRepository.findById(eventId).get();

        List<Comment> eventCommentsList = new ArrayList<Comment>();

        List<AppUser> blockedUsers = currentUser.getBlockedUsers();

        for (Comment comment : event.getComments()) {
            String commentCreator = comment.getOwnersUsername();

            for (AppUser blockedUser : blockedUsers) {
                if (!blockedUser.getEmail().equals(commentCreator)) {
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
        boolean isOrganizer = creator.getRoles().contains(AppUserRole.ORGANIZER);
        if (isOrganizer) {
            event.setStatus(Event.ACCEPTED_STATUS);
        } else {
            event.setStatus(Event.PENDING_STATUS);
        }
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(creator);
        event.setAppUsers(appUsers);
        // TODO: Now its possible to create event with nulls for everything. 
        // Create validation for Name, localDateTime, status.

        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, String username) throws EventOrganizerException {
        if (!eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event does not exist");
        }
        AppUser creator = (AppUser) appUserService.loadUserByUsername(username);

        event.setCreator(creator);

        return eventRepository.save(event);
    }

    public Event acceptEvent(long eventId, String currentUserEmail) {
        Event eventToUpdate = this.getEventById(eventId, currentUserEmail);
        eventToUpdate.setStatus(Event.ACCEPTED_STATUS);
        return eventRepository.save(eventToUpdate);
    }

    public Event rejectEvent(long eventId, String currentUserEmail) {
        Event eventToUpdate = this.getEventById(eventId, currentUserEmail);
        eventToUpdate.setStatus(Event.REJECTED_STATUS);
        return eventRepository.save(eventToUpdate);
    }

    public void deleteEvent(Event event) throws EventOrganizerException {
        if (!eventRepository.existsById(event.getId())) {
            throw new EventOrganizerException("Event does not exist");
        }
        eventRepository.delete(event);
    }

    public void addComment(String comment, Long eventId, String username) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }
        Event event = eventRepository.findById(eventId).get();
        List<Comment> allComments = event.getComments();

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
        List<Image> allImages = event.getImages();

        Image imageModel = new Image();
        imageModel.setUrl(image);
        imageModel.setOwnerUsername(username);

        allImages.add(imageModel);
        event.setImages(allImages);

        eventRepository.save(event);
    }

    public void addFeedback(Integer rating, String comment, Long eventId, String username) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }

        Event event = eventRepository.findById(eventId).get();
        List<Feedback> allFeedbacks = event.getFeedbacks();

        if (allFeedbacks == null) {
            allFeedbacks = new ArrayList<Feedback>();
        }

        Feedback feedbackModel = new Feedback();
        feedbackModel.setRating(rating);
        feedbackModel.setComment(comment);
        feedbackModel.setOwnerUsername(username);

        allFeedbacks.add(feedbackModel);
        event.setFeedbacks(allFeedbacks);

        eventRepository.save(event);
    }

    // TODO: RENAME
    public void userIsInterestedInEvent(String username, Long eventId) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }

        Event event = eventRepository.findById(eventId).get();
        List<AppUser> allUsersInterested = event.getUsersInterested();

        if (allUsersInterested == null) {
            allUsersInterested = new ArrayList<AppUser>();
        }

        AppUser userModel = appUserService.findUserByEmail(username).get();

        allUsersInterested.add(userModel);
        event.setUsersInterested(allUsersInterested);

        eventRepository.save(event);
    }

    public void removeUserInterestedInEvent (String username, Long eventId) throws EventOrganizerException{
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }

        Event event = eventRepository.findById(eventId).get();
        AppUser userModel = appUserService.findUserByEmail(username).get();
        List<AppUser> allUsersInterested = new ArrayList<>(event.getUsersInterested());

        allUsersInterested.remove(userModel);
        event.setUsersInterested(allUsersInterested);
        eventRepository.save(event);
    }

    // TODO: RENAME
    public void userIsGoingToEvent(String username, Long eventId) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }

        Event event = eventRepository.findById(eventId).get();
        List<AppUser> allUsersGoing = event.getUsersGoing();

        if (allUsersGoing == null) {
            allUsersGoing = new ArrayList<AppUser>();
        }

        AppUser userModel = appUserService.findUserByEmail(username).get();

        allUsersGoing.add(userModel);
        event.setUsersGoing(allUsersGoing);

        eventRepository.save(event);
    }

    public void removeUserGoingToEvent (String username, Long eventId) throws EventOrganizerException{
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if(!eventOptional.isPresent()){
            throw new EventOrganizerException("Event does not exist");
        }

        Event event = eventOptional.get();
        List<AppUser> usersGoing = new ArrayList<>(event.getUsersGoing());
        AppUser userToRemove = usersGoing.stream()
                .filter(user -> user.getEmail().equals(username))
                .findFirst()
                .orElse(null);

        if (userToRemove != null) {
            usersGoing.remove(userToRemove);
            event.setUsersGoing(usersGoing);
            eventRepository.save(event);
        }
    }

    public List<Event> getThisWeeksEvents() {
        List<Event> allEvents = eventRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfTheWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfTheWeek = startOfTheWeek.plusWeeks(1);

        List<Event> eventsThisWeek = allEvents.stream()
                .filter(event -> event.getStartDate().isAfter(startOfTheWeek) && event.getEndDate().isBefore(endOfTheWeek))
                .collect(Collectors.toList());

        return eventsThisWeek;
    }

    public List<Event> getThisMonthsEvents() {
        List<Event> allEvents = eventRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfTheMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfTheMonth = now.with(TemporalAdjusters.lastDayOfMonth());

        List<Event> eventsThisWeek = allEvents.stream()
                .filter(event -> event.getStartDate().isAfter(startOfTheMonth) && event.getEndDate().isBefore(endOfTheMonth))
                .collect(Collectors.toList());

        return eventsThisWeek;
    }

    public List<Event> getEventsByLocation(String location) {
        List<Event> allEvents = eventRepository.findAll();
        List<Event> localEvents = allEvents.stream().filter(event -> event.getLocation().contains(location))
                .collect(Collectors.toList());

        return localEvents;
    }

    public List<AppUser> getUsersInterestedInEvent(long eventId) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }
        Event event = eventRepository.findById(eventId).get();
        List<AppUser> interestedUsersList = event.getUsersInterested();

        return interestedUsersList;
    }

    public List<AppUser> getUsersGoingToEvent(long eventId) throws EventOrganizerException {
        if (!eventRepository.existsById(eventId)) {
            throw new EventOrganizerException("Event does not exist");
        }
        Event event = eventRepository.findById(eventId).get();
        List<AppUser> goingUsersList = event.getUsersGoing();

        return goingUsersList;
    }

    public List<Event> getEventsByType(String type) {
        List<Event> allEvents = eventRepository.findAll();
        List<Event> eventsByTypeList = allEvents.stream().filter(event -> event.getEventType().equals(type))
                .collect(Collectors.toList());

        return eventsByTypeList;
    }

    public List<Event> getHostingEvents(String username) {
        AppUser userModel = appUserService.findUserByEmail(username).get();
        List<Event> userEvents = userModel.getEvents();

        return userEvents;
    }

    public List<Event> getEventsByDateRange(LocalDateTime startRange, LocalDateTime endRange) {
        List<Event> allEvents = eventRepository.findAll();

        List<Event> eventsInRangeList = allEvents.stream()
                .filter(event -> event.getStartDate().isAfter(startRange) && event.getEndDate().isBefore(endRange))
                .collect(Collectors.toList());

        return eventsInRangeList;
    }

    public List<Event> getMyInterestedEvents(String username) {
        AppUser userModel = appUserService.findUserByEmail(username).get();
        
        List<Event> allEvents = eventRepository.findAll().stream()
            .filter(event -> event.getUsersInterested().stream().anyMatch(user->user.getUsername().equals(username)))
            .collect(Collectors.toList());

        return allEvents;
    }

    public List<Event> getMyGoingToEvents(String username) {
        AppUser userModel = appUserService.findUserByEmail(username).get();
        
        List<Event> allEvents = eventRepository.findAll().stream()
            .filter(event -> event.getUsersGoing().stream().anyMatch(user->user.getUsername().equals(username)))
            .collect(Collectors.toList());

        return allEvents;
    }
}
