package com.event.organizer.api;

import com.event.organizer.api.appuser.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = {EventRepository.class, EventService.class, Event.class, Comment.class})
@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        		classes = {EventRepository.class, EventService.class, Event.class, Comment.class})
class EventOrganizerApplicationTests {

	@Test
	void GivenExistingEvent_WhenAddingComment_CommentIsAdded() throws EventOrganizerException {
		// Create an event and a comment to add to the event
		Event event = new Event();
		event.setId(1L);
		event.setComments(new ArrayList<>());
		Comment comment = new Comment();
		comment.setContent("This is a comment");

		// Set up a mock event repository that will return the event when findById is called
		List<Comment> comments = new ArrayList<>();
		comments.add(comment);
		EventRepository eventRepository = mock(EventRepository.class);
		
		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the addComment method
		UserRepository userRepository = mock(UserRepository.class);
		EventService eventService = new EventService(eventRepository, userRepository);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
		eventService.addComment(comment.getContent(), event.getId());

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
		when(eventRepository.findById(1L)).thenReturn(Optional.empty());

		// Create an EventOrganizer instance and call the addComment method
		UserRepository userRepository = mock(UserRepository.class);
		EventService eventService = new EventService(eventRepository, userRepository);

		// Verify that an EventOrganizerException is thrown when the event does not
		// exist
		assertThrows(EventOrganizerException.class, () -> {
			eventService.addComment(comment.getContent(), 0L);
		});
	}
}