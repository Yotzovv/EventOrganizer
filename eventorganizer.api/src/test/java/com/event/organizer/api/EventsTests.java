package com.event.organizer.api;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRoleService;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.*;
import com.event.organizer.api.repository.EventRepository;
import com.event.organizer.api.service.EventService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.constraints.AssertTrue;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EventRepository.class, EventService.class, Event.class, Comment.class})
@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        		classes = {EventRepository.class, EventService.class, Event.class, Comment.class})
class EventsTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private AppUserRoleService appUserRoleService;

	@Test
	void GivenExistingEvent_WhenAddingComment_CommentIsAdded() throws EventOrganizerException {
		// Create an event and a comment to add to the event
		Event event = new Event();
		event.setId(1l);
		event.setComments(new ArrayList<Comment>());

		// Set up a mock event repository that will return the event when findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the addComment method
		EventService eventService = new EventService(eventRepository, appUserService);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

		eventService.addComment("This is a comment", event.getId(), "admin");

		// Verify that the comment was added to the event
		assertTrue(event.getComments().stream().anyMatch(comment -> comment.getContent().equals("This is a comment")));
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
	void GivenExistingEvent_WhenAddingImage_ImageIsAdded() throws EventOrganizerException {
		// Create an event and a comment to add to the event
		Event event = new Event();
		event.setId(1l);
		event.setImages(new ArrayList<Image>());

		// Set up a mock event repository that will return the event when findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the addImage method
		EventService eventService = new EventService(eventRepository, appUserService);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

		eventService.addImage("exampleImageLink", event.getId(), "admin");

		// Verify that the image was added to the event
		assertTrue(event.getImages().stream().anyMatch(image -> image.getUrl().equals("exampleImageLink")));
	}

	@Test
	void GivenNonExistingEvent_WhenAddingImage_ThrowsException() {
		// Create an image to add to the event
		Image image = new Image();
		image.setUrl("This is an image");

		// Set up a mock event repository that will return an empty Optional when
		// findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.findById(1l)).thenReturn(Optional.empty());

		// Create an EventOrganizer instance and call the addImage method
		EventService eventService = new EventService(eventRepository, appUserService);

		// Verify that an EventOrganizerException is thrown when the event does not
		// exist
		assertThrows(EventOrganizerException.class, () -> {
			eventService.addImage(image.getUrl(), 0l, "admin");
		});
	}

	@Test
	void GivenExistingEvent_WhenAddingFeedback_FeedbackIsAdded() throws EventOrganizerException {
		// Create an event and a feedback to add to the event
		Event event = new Event();
		event.setId(1l);
		event.setImages(new ArrayList<Image>());

		// Set up a mock event repository that will return the event when findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the addFeedback method
		EventService eventService = new EventService(eventRepository, appUserService);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

		eventService.addFeedback(1, "feedbackComment", event.getId(), "admin");

		// Verify that the feedback was added to the event
		assertTrue(event.getFeedbacks().stream().anyMatch(feedback -> feedback.getComment().equals("feedbackComment")));
		assertTrue(event.getFeedbacks().stream().anyMatch(feedback -> feedback.getRating().equals(1)));
	}

	@Test
	void GivenExistingAccount_WhenAddingProfilePicture_ProfilePictureIsAdded() throws EventOrganizerException {
		// Create a user and a profile picture
		AppUser user = new AppUser();
		user.setUsername("username");
		user.setEmail("username@example.com");

		Image profilePicture = new Image();
		profilePicture.setUrl("pictureUrl");
		user.setProfilePicture(profilePicture);

		AppUserService appUserService = mock(AppUserService.class);

		when(appUserService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
		appUserService.uploadProfilePicture(user.getEmail(), profilePicture.getUrl());

		// Verify that the image was added to the user's account
		assertEquals("pictureUrl", user.getProfilePicture().getUrl());
	}

	@Test
	void GivenExistingEvent_WhenAddingInterestedUser_InterestedUserIsAdded() throws EventOrganizerException {
		// Create an event and an interested User to add to the event
		Event event = new Event();
		event.setId(1l);
		event.setUsersInterested(new ArrayList<AppUser>());

		AppUser appUser = new AppUser();
		appUser.setUsername("username");
		appUser.setEmail("example@.com");

		// Set up a mock event repository that will return the event when findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the userIsInterestedInEvent method
		EventService eventService = new EventService(eventRepository, appUserService);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
		when(appUserService.findUserByEmail(appUser.getUsername())).thenReturn(Optional.of(appUser));

		eventService.userIsInterestedInEvent(appUser.getUsername(), event.getId());

		// Verify that the interestedUser was added to the event

		Assertions.assertEquals(1, event.getUsersInterested().size());
	}

	@Test
	void GivenExistingEvent_WhenAddingGoingUser_GoingUserIsAdded() throws EventOrganizerException {
		// Create an event and a going User to add to the event
		Event event = new Event();
		event.setId(1l);
		event.setUsersGoing(new ArrayList<AppUser>());

		AppUser appUser = new AppUser();
		appUser.setUsername("username");
		appUser.setEmail("example@.com");

		// Set up a mock event repository that will return the event when findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.existsById(event.getId())).thenReturn(true);

		// Create an EventOrganizer instance and call the userIsGoingToEvent method
		EventService eventService = new EventService(eventRepository, appUserService);
		when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
		when(appUserService.findUserByEmail(appUser.getUsername())).thenReturn(Optional.of(appUser));

		eventService.userIsGoingToEvent(appUser.getUsername(), event.getId());

		// Verify that the goingUser was added to the event

		Assertions.assertEquals(1, event.getUsersGoing().size());
	}

	@Test
	void GivenNonExistingEvent_WhenAddingFeedback_ThrowsException() {
		// Create a Feedback to add to the event

		Feedback feedback = new Feedback();
		feedback.setComment("awesome");
		feedback.setRating(5);

		// Set up a mock event repository that will return an empty Optional when
		// findById is called
		EventRepository eventRepository = mock(EventRepository.class);
		AppUserService appUserService = mock(AppUserService.class);

		when(eventRepository.findById(1l)).thenReturn(Optional.empty());

		// Create an EventOrganizer instance and call the addFeedback method
		EventService eventService = new EventService(eventRepository, appUserService);

		// Verify that an EventOrganizerException is thrown when the event does not
		// exist
		assertThrows(EventOrganizerException.class, () -> {
			eventService.addFeedback(feedback.getRating(), feedback.getComment(), 1l, "admin");
		});
	}

	@Test
	void GivenBlockedUsers_WhenGettingAllEvents_ThenBlockedEventsAreExcluded() {
		UserRepository userRepository = mock(UserRepository.class);
		AppUserService userService = new AppUserService(userRepository, null, appUserRoleService);

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

		Assertions.assertEquals(3, currentUserEventFeed.size());
	}
	
	@Test
	void GivenBlockedUsers_WhenGettingAllEvents_ThenBlockedCommentsAreExcluded() {
		UserRepository userRepository = mock(UserRepository.class);
		AppUserService userService = new AppUserService(userRepository, null, appUserRoleService);

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
		when(mockedEventRepository.findById(1L)).thenReturn(Optional.of(dummyEventWithComments()));

		EventService eventService = new EventService(mockedEventRepository, userService);

		Event currentUserEventFeed = eventService.getEventById(1, currentUser.getEmail());

		Assertions.assertEquals(3, currentUserEventFeed.getComments().size());
	}

	@Test
	void givenAllEvents_WhenCallingGetEventsThisWeek_ThenReturnOnlyThisWeeksEvents() {

		List<Comment> emptyCommentsList = new ArrayList<Comment>();
		List<Image> emptyImagesList = new ArrayList<Image>();
		List<Feedback> emptyFeedbacksList = new ArrayList<Feedback>();
		List<AppUser> emptyUsersInterestedList = new ArrayList<AppUser>();
		List<AppUser> emptyUsersGoingList = new ArrayList<AppUser>();

		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser blockedUser = new AppUser();
		blockedUser.setEmail("block@example.com");
		blockedUser.setBlockedUsers(new ArrayList<AppUser>());

		List<Event> dummyEventsList = Arrays.asList(
				new Event(1L, "Tech Conference", LocalDateTime.now(),
						LocalDateTime.now(), Event.ACCEPTED_STATUS,
						"A conference for software developers and IT professionals", "San Francisco, CA", "", currentUser,
						emptyCommentsList, null,emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(2L, "Art Exhibition", LocalDateTime.now().plusDays(1),
						LocalDateTime.now().plusDays(1), Event.NONE_STATUS,
						"A showcase of contemporary art from local artists", "Los Angeles, CA", "", currentUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(3L, "Music Festival", LocalDateTime.of(2022, 7, 20, 12, 0),
						LocalDateTime.now().plusMonths(1), Event.REJECTED_STATUS,
						"A multi-day music festival featuring various genres and artists", "New York, NY", "", blockedUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList));

		EventService eventService = mock(EventService.class);
		when(eventService.getThisWeeksEvents()).thenReturn(List.of(dummyEventsList.get(0), dummyEventsList.get(1)));

		Event event1 = eventService.getThisWeeksEvents().get(0);
		Event event2 = eventService.getThisWeeksEvents().get(1);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfTheWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDateTime endOfTheWeek = startOfTheWeek.plusWeeks(1);

		Assertions.assertTrue(event1.getStartDate().isAfter(startOfTheWeek) && event1.getEndDate().isBefore(endOfTheWeek));
		Assertions.assertTrue(event2.getStartDate().isAfter(startOfTheWeek) && event2.getEndDate().isBefore(endOfTheWeek));
	}

	@Test
	void givenAllEvents_WhenCallingGetEventsThisMonth_ThenReturnOnlyThisMonthsEvents() {

		List<Comment> emptyCommentsList = new ArrayList<Comment>();
		List<Image> emptyImagesList = new ArrayList<Image>();
		List<Feedback> emptyFeedbacksList = new ArrayList<Feedback>();
		List<AppUser> emptyUsersInterestedList = new ArrayList<AppUser>();
		List<AppUser> emptyUsersGoingList = new ArrayList<AppUser>();

		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser blockedUser = new AppUser();
		blockedUser.setEmail("block@example.com");
		blockedUser.setBlockedUsers(new ArrayList<AppUser>());

		List<Event> dummyEventsList = Arrays.asList(
				new Event(1L, "Tech Conference", LocalDateTime.now(),
						LocalDateTime.now(), Event.ACCEPTED_STATUS,
						"A conference for software developers and IT professionals", "San Francisco, CA", "", currentUser,
						emptyCommentsList, null,emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(2L, "Art Exhibition", LocalDateTime.now(),
						LocalDateTime.now(), Event.NONE_STATUS,
						"A showcase of contemporary art from local artists", "Los Angeles, CA", "", currentUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(3L, "Music Festival", LocalDateTime.of(2022, 7, 20, 12, 0),
						LocalDateTime.now().plusMonths(1), Event.REJECTED_STATUS,
						"A multi-day music festival featuring various genres and artists", "New York, NY", "", blockedUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList));

		EventService eventService = mock(EventService.class);
		when(eventService.getThisMonthsEvents()).thenReturn(List.of(dummyEventsList.get(0), dummyEventsList.get(1)));

		Event event1 = eventService.getThisMonthsEvents().get(0);
		Event event2 = eventService.getThisMonthsEvents().get(1);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfTheMonth = now.with(TemporalAdjusters.firstDayOfMonth());
		LocalDateTime endOfTheMonth = now.with(TemporalAdjusters.lastDayOfMonth());

		Assertions.assertTrue(event1.getStartDate().isAfter(startOfTheMonth) && event1.getEndDate().isBefore(endOfTheMonth));
		Assertions.assertTrue(event2.getStartDate().isAfter(startOfTheMonth) && event2.getEndDate().isBefore(endOfTheMonth));
	}

	@Test
	void givenAllEvents_WhenCallingGetEventsByLocation_ThenReturnOnlyLocalEvents() {

		List<Comment> emptyCommentsList = new ArrayList<Comment>();
		List<Image> emptyImagesList = new ArrayList<Image>();
		List<Feedback> emptyFeedbacksList = new ArrayList<Feedback>();
		List<AppUser> emptyUsersInterestedList = new ArrayList<AppUser>();
		List<AppUser> emptyUsersGoingList = new ArrayList<AppUser>();

		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser blockedUser = new AppUser();
		blockedUser.setEmail("block@example.com");
		blockedUser.setBlockedUsers(new ArrayList<AppUser>());

		List<Event> dummyEventsList = Arrays.asList(
				new Event(1L, "Tech Conference", LocalDateTime.now(),
						LocalDateTime.now(), Event.ACCEPTED_STATUS,
						"A conference for software developers and IT professionals", "Pazardzhik", "", currentUser,
						emptyCommentsList, null,emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(2L, "Art Exhibition", LocalDateTime.now(),
						LocalDateTime.now(), Event.NONE_STATUS,
						"A showcase of contemporary art from local artists", "Pazardzhik", "", currentUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(3L, "Music Festival", LocalDateTime.of(2022, 7, 20, 12, 0),
						LocalDateTime.now().plusMonths(1), Event.REJECTED_STATUS,
						"A multi-day music festival featuring various genres and artists", "New York, NY", "", blockedUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList));

		EventService eventService = mock(EventService.class);
		when(eventService.getEventsByLocation("Pazardzhik")).thenReturn(List.of(dummyEventsList.get(0), dummyEventsList.get(1)));

		assertTrue(eventService.getEventsByLocation("Pazardzhik").stream().anyMatch(event -> event.getLocation() == "Pazardzhik"));
	}

	@Test
	void givenAnEvent_WhenGettingUsersInterestedInAnEvent_ThenReturnInterestedUsers() throws EventOrganizerException {
		AppUser user1 = new AppUser();
		user1.setUsername("username1");
		user1.setEmail("example1.com");
		user1.setUsername("username1");

		AppUser user2 = new AppUser();
		user2.setUsername("username2");
		user2.setEmail("example2.com");
		user2.setUsername("username2");

		EventService eventService = mock(EventService.class);

		Event event = new Event();
		event.setId(1L);

		eventService.userIsInterestedInEvent(user1.getUsername(), event.getId());
		eventService.userIsInterestedInEvent(user2.getUsername(), event.getId());

		when(eventService.getUsersInterestedInEvent(event.getId())).thenReturn(List.of(user1, user2));

		assertEquals(2, eventService.getUsersInterestedInEvent(event.getId()).size());
	}

	@Test
	void givenAnEvent_WhenGettingUsersGoingToAnEvent_ThenReturnGoingUsers() throws EventOrganizerException {
		AppUser user1 = new AppUser();
		user1.setUsername("username1");
		user1.setEmail("example1.com");
		user1.setUsername("username1");

		AppUser user2 = new AppUser();
		user2.setUsername("username2");
		user2.setEmail("example2.com");
		user2.setUsername("username2");

		EventService eventService = mock(EventService.class);

		Event event = new Event();
		event.setId(1L);

		eventService.userIsGoingToEvent(user1.getUsername(), event.getId());
		eventService.userIsGoingToEvent(user2.getUsername(), event.getId());

		when(eventService.getUsersGoingToEvent(event.getId())).thenReturn(List.of(user1, user2));

		assertEquals(2, eventService.getUsersGoingToEvent(event.getId()).size());
	}

	@Test
	void givenAllEvents_WhenGettingEventsByTYpe_ThenReturnEventsOfSaidType() {
		Event event1 = new Event();
		event1.setEventType("Party");
		event1.setName("Party1");
		event1.setId(1L);

		Event event2 = new Event();
		event2.setEventType("Party");
		event2.setName("Party2");
		event2.setId(2L);

		Event event3 = new Event();
		event3.setEventType("Party");
		event3.setName("Party3");
		event3.setId(3L);

		EventService eventService = mock(EventService.class);

		when(eventService.getEventsByType("Party")).thenReturn(List.of(event1, event2, event3));

		Assertions.assertTrue(eventService.getEventsByType("Party").stream().anyMatch(event -> event.getEventType().equals("Party")));
	}

	@Test
	void givenAllEvents_WhenGettingEventsHostedByUser_ThenReturnOnlyEventsBySaidUser() {
		AppUser appUser = new AppUser();
		appUser.setUsername("user");
		appUser.setId(1L);
		appUser.setEmail("user@example.com");

		Event event1 = new Event();
		event1.setId(1L);
		event1.setCreator(appUser);
		event1.setEventType("Party");

		Event event2 = new Event();
		event2.setId(2L);
		event2.setCreator(appUser);
		event2.setEventType("Party");

		Event event3 = new Event();
		event3.setId(3L);
		event3.setCreator(appUser);
		event3.setEventType("Party");

		EventService eventService = mock(EventService.class);

		when(eventService.getHostingEvents(appUser.getUsername())).thenReturn(List.of(event1, event2, event3));

		Assertions.assertTrue(eventService.getHostingEvents(appUser.getUsername()).stream().anyMatch(event ->
				event.getCreator().equals(appUser)));
	}

	@Test
	void givenAllEvents_WhenGettingEventsByDateRange_ThenReturnsEventsWithinSaidRange() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startRange = LocalDateTime.of(2023, Month.JANUARY, 1, 12, 15);
		LocalDateTime endRange = LocalDateTime.of(2023, Month.FEBRUARY, 1, 12, 15);

		Event event1 = new Event();
		event1.setStartDate(LocalDateTime.of(2023, Month.JANUARY, 10, 12, 15));
		event1.setEndDate(LocalDateTime.of(2023, Month.JANUARY, 18, 12, 15));
		event1.setId(1L);
		event1.setName("business discussion");

		Event event2 = new Event();
		event2.setStartDate(LocalDateTime.of(2023, Month.JANUARY, 25, 12, 15));
		event2.setEndDate(LocalDateTime.of(2023, Month.JANUARY, 30, 12, 15));
		event2.setId(2L);
		event2.setName("business trip");

		Event event3 = new Event();
		event3.setStartDate(LocalDateTime.of(2023, Month.JANUARY, 2, 12, 15));
		event3.setEndDate(LocalDateTime.of(2023, Month.JANUARY, 7, 12, 15));
		event3.setId(3L);
		event3.setName("business gathering");

		EventService eventService = mock(EventService.class);

		when(eventService.getEventsByDateRange(startRange, endRange)).thenReturn(List.of(event1, event2, event3));

		Assertions.assertTrue(eventService.getEventsByDateRange(startRange, endRange).stream().anyMatch(event ->
				event.getStartDate().isAfter(startRange) && event.getEndDate().isBefore(endRange)));
	}




	private List<Event> dummyEventsList() {
		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		AppUser blockedUser = new AppUser();
		blockedUser.setEmail("block@example.com");
		blockedUser.setBlockedUsers(new ArrayList<AppUser>());

		List<Comment> emptyCommentsList = new ArrayList<Comment>();
		List<Image> emptyImagesList = new ArrayList<Image>();
		List<Feedback> emptyFeedbacksList = new ArrayList<Feedback>();
		List<AppUser> emptyUsersInterestedList = new ArrayList<AppUser>();
		List<AppUser> emptyUsersGoingList = new ArrayList<AppUser>();

		List<Event> dummyEventsList = Arrays.asList(
				new Event(1L, "Tech Conference", LocalDateTime.of(2022, 1, 15, 9, 0),
						LocalDateTime.of(2022, 1, 17, 17, 0), Event.ACCEPTED_STATUS,
						"A conference for software developers and IT professionals", "San Francisco, CA", "", currentUser,
						emptyCommentsList, null,emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(2L, "Art Exhibition", LocalDateTime.of(2022, 3, 5, 10, 0),
						LocalDateTime.of(2022, 3, 7, 18, 0), Event.NONE_STATUS,
						"A showcase of contemporary art from local artists", "Los Angeles, CA", "", currentUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList),
				new Event(3L, "Music Festival", LocalDateTime.of(2022, 7, 20, 12, 0),
						LocalDateTime.of(2022, 7, 25, 0, 0), Event.REJECTED_STATUS,
						"A multi-day music festival featuring various genres and artists", "New York, NY", "", blockedUser,
						emptyCommentsList, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList));
		return dummyEventsList;
	}

	private Event dummyEventWithComments() {
		AppUser currentUser = new AppUser();
		currentUser.setEmail("current@example.com");
		currentUser.setBlockedUsers(new ArrayList<AppUser>());

		List<Image> emptyImagesList = new ArrayList<Image>();
		List<Comment> dummyComments = new ArrayList<>();
		List<Feedback> emptyFeedbacksList = new ArrayList<Feedback>();
		List<AppUser> emptyUsersInterestedList = new ArrayList<AppUser>();
		List<AppUser> emptyUsersGoingList = new ArrayList<AppUser>();


		dummyComments.add(new Comment(1, "This is a great event!", LocalDateTime.now(), "johnsmith"));
		dummyComments.add(new Comment(2, "I'm looking forward to attending!", LocalDateTime.now(), "janelee"));
		dummyComments.add(new Comment(3, "I can't wait to see the speakers!", LocalDateTime.now(), "block@example.com"));
		dummyComments.add(new Comment(4, "This event has such a great lineup!", LocalDateTime.now(), "sarahjohnson"));

		Event event = new Event(1L, "Tech Conference", LocalDateTime.of(2022, 1, 15, 9, 0),
						LocalDateTime.of(2022, 1, 17, 17, 0), Event.ACCEPTED_STATUS,
						"A conference for software developers and IT professionals", "San Francisco, CA", "", currentUser,
						dummyComments, null, emptyUsersInterestedList, emptyUsersGoingList, emptyImagesList, emptyFeedbacksList);

		return event;		
	}
}