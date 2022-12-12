package com.event.organizer.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventOrganizerApplicationTests {

	@Test
	void contextLoads() {
	}
		@Test
		void testAddComment_eventExists() throws EventOrganizerException {
			// Create an event and a comment to add to the event
			Event event = new Event();
			event.setId(1);
			Comment comment = new Comment();
			comment.setText("This is a comment");

			// Set up a mock event repository that will return the event when findById is called
			EventRepository eventRepository = mock(EventRepository.class);
			when(eventRepository.findById(1)).thenReturn(Optional.of(event));

			// Create an EventOrganizer instance and call the addComment method
			EventOrganizer eventOrganizer = new EventOrganizer(eventRepository);
			eventOrganizer.addComment(comment, event);

			// Verify that the comment was added to the event
			assertTrue(event.getComments().contains(comment));

			// Verify that the save method was called on the event repository
			verify(eventRepository).save(event);
		}

		@Test
		void testAddComment_eventDoesNotExist() {
			// Create a comment to add to the event
			Comment comment = new Comment();
			comment.setText("This is a comment");

			// Set up a mock event repository that will return an empty Optional when findById is called
			EventRepository eventRepository = mock(EventRepository.class);
			when(eventRepository.findById(1)).thenReturn(Optional.empty());

			// Create an EventOrganizer instance and call the addComment method
			EventOrganizer eventOrganizer = new EventOrganizer(eventRepository);

			// Verify that an EventOrganizerException is thrown when the event does not exist
			assertThrows(EventOrganizerException.class, () -> {
				eventOrganizer.addComment(comment, new Event());
			});
		}
	}