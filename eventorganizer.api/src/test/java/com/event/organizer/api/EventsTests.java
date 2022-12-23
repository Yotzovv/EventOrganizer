package com.event.organizer.api;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Comment;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.repository.EventRepository;
import com.event.organizer.api.service.EventService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = {EventRepository.class, EventService.class, Event.class, Comment.class})
@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        		classes = {EventRepository.class, EventService.class, Event.class, Comment.class})
class EventTests {

	@Test
	void GivenExistingEvent_WhenAddingComment_CommentIsAdded() throws EventOrganizerException {
		// Create an event and a comment to add to the event
		Event event = new Event();
		event.setId(1l);
		event.setComments(new ArrayList<Comment>());
		Comment comment = new Comment();
		comment.setContent("This is a comment");

		// Set up a mock event repository that will return the event when findById is called
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the addComment method
		EventService eventService = new EventService(eventRepository, appUserService);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
		eventService.addComment(comment.getContent(), event.getId(), "admin");

		// Verify that the comment was added to the event
		assertTrue(event.getComments().contains(comment));

		// Verify that the save method was called on the event repository
		verify(eventRepository).save(event);
	}

	@Test
	void GivenNonExistingEvent_WhenAddingComment_ThrowsException() {
		// Create a comment to add to the event
		Comment comment = new Comment();
		comment.setContent("This is a comment");

		// Set up a mock event repository that will return an empty Optional when
		// findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.findById(1l)).thenReturn(Optional.empty());

		// Create an EventOrganizer instance and call the addComment method
		EventService eventService = new EventService(eventRepository, appUserService);

		// Verify that an EventOrganizerException is thrown when the event does not
		// exist
		assertThrows(EventOrganizerException.class, () -> {
			eventService.addComment(comment.getContent(), 0l, "admin");
		});
	}

	@Test
	void GivenBlockedUsers_WhenGettingAllEvents_ThenBlockedEventsAreExcluded() {
		UserRepository userRepository = mock(UserRepository.class);
		AppUserService userService = new AppUserService(userRepository, null);

		// Set up mock user data
		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser blockedUser = new AppUser();
		blockedUser.setEmail("block@example.com");
		blockedUser.setBlockedUsers(new ArrayList<AppUser>());

		when(userRepository.findByEmail("current@example.com")).thenReturn(Optional.of(currentUser));
		when(userRepository.findByEmail("block@example.com")).thenReturn(Optional.of(blockedUser));

		// Invoke the method under test
		userService.blockUser("current@example.com", "block@example.com");

		EventRepository mockedEventRepository = mock(EventRepository.class);
		when(mockedEventRepository.findAll()).thenReturn(dummyEventsList());

		EventService eventService = new EventService(mockedEventRepository, userService);

		List<Event> currentUserEventFeed = eventService.findAll(currentUser.getEmail());

		Assertions.assertEquals(2, currentUserEventFeed.size());
	}

	private List<Event> dummyEventsList() {
		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser blockedUser = new AppUser();
		blockedUser.setEmail("block@example.com");
		blockedUser.setBlockedUsers(new ArrayList<AppUser>());

		List<Comment> emptyCommentsList = new ArrayList<Comment>();

		List<Event> dummyEventsList = Arrays.asList(
				new Event(1L, "Tech Conference", LocalDateTime.of(2022, 1, 15, 9, 0),
						LocalDateTime.of(2022, 1, 17, 17, 0), Event.ACCEPTED_STATUS,
						"A conference for software developers and IT professionals", "San Francisco, CA", currentUser,
						emptyCommentsList, null),
				new Event(2L, "Art Exhibition", LocalDateTime.of(2022, 3, 5, 10, 0),
						LocalDateTime.of(2022, 3, 7, 18, 0), Event.NONE_STATUS,
						"A showcase of contemporary art from local artists", "Los Angeles, CA", currentUser,
						emptyCommentsList, null),
				new Event(3L, "Music Festival", LocalDateTime.of(2022, 7, 20, 12, 0),
						LocalDateTime.of(2022, 7, 25, 0, 0), Event.REJECTED_STATUS,
						"A multi-day music festival featuring various genres and artists", "New York, NY", blockedUser,
						emptyCommentsList, null));
		return dummyEventsList;
	}
}