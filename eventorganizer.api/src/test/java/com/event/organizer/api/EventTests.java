package com.event.organizer.api;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.repository.EventRepository;
import com.event.organizer.api.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EventRepository.class, EventService.class, Event.class})
@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        classes = {EventRepository.class, EventService.class, Event.class})
class EventTests {

    @Test
    void GivenValidData_WhenSaveEvent_ThenSaveSuccessfully() throws EventOrganizerException {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().plusYears(1));
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.save(event)).thenReturn(event);

        EventService eventService = new EventService(eventRepository);

        Event savedEvent = eventService.saveEvent(event);

        Assertions.assertNotNull(savedEvent);
        Assertions.assertNotNull(savedEvent.getId());
        Assertions.assertEquals(event.getTime(), savedEvent.getTime());
        Assertions.assertEquals(event.getName(), savedEvent.getName());
    }

    @Test
    void GivenDuplicateID_WhenSaveEvent_ShouldThrowException() throws EventOrganizerException {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now());
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        Event event2 = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now());
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.save(event)).thenReturn(event);
        when(eventRepository.existsById(event.getId())).thenReturn(true);

        EventService eventService = new EventService(eventRepository);

        EventOrganizerException thrown =
                Assertions.assertThrows(EventOrganizerException.class, () -> eventService.saveEvent(event));

        assertTrue(thrown.getMessage().contentEquals("Event already exist"));
    }

    @Test
    void GivenInvalidDate_WhenSaveEvent_ThenThrowError() throws EventOrganizerException {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().minusMonths(50));
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.save(event)).thenReturn(event);

        EventService eventService = new EventService(eventRepository);

        EventOrganizerException thrown =
                Assertions.assertThrows(EventOrganizerException.class, () -> eventService.saveEvent(event));

        assertTrue(thrown.getMessage().contentEquals("Event can not be created prior to current date"));
    }

    @Test
    void WhenFindAll_ThenReturnsCorrectData() {
        EventRepository eventRepository = mock(EventRepository.class);
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().minusMonths(50));
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        List<Event> eventList = new ArrayList();
        eventList.add(event);

        when(eventRepository.findAll()).thenReturn(eventList);

        List<Event> eventList2 = eventRepository.findAll();

        Assertions.assertEquals(1, eventList2.size());
    }

    @Test
    void GivenEventThatDoesNotExist_WhenUpdateEvent_ThenThrowException() throws EventOrganizerException {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().plusYears(1));
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        EventRepository eventRepository = mock(EventRepository.class);

        when(eventRepository.save(event)).thenReturn(event);

        EventService eventService = new EventService(eventRepository);

        eventService.saveEvent(event);

        long newId = 2;
        String newName = Event.ACCEPTED_STATUS;
        LocalDateTime newDate = LocalDateTime.of(2023, 9, 6, 5, 5);
        String newStatus = Event.ACCEPTED_STATUS;

        EventOrganizerException thrown =
                Assertions.assertThrows(EventOrganizerException.class, () -> when(eventService.updateEvent(event)).thenReturn(new Event(newId, newName, newDate, newStatus)));

        assertTrue(thrown.getMessage().contentEquals("Event does not exist"));
    }

    @Test
    void GivenEventThatDoesNotExist_WhenDeleteEvent_ThenThrowException() throws EventOrganizerException {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().plusYears(1));
        event.setStatus(Event.ACCEPTED_STATUS);
        event.setId(1L);

        EventRepository eventRepository = mock(EventRepository.class);

        when(eventRepository.save(event)).thenReturn(event);

        EventService eventService = new EventService(eventRepository);

        eventService.saveEvent(event);

        EventOrganizerException thrown =
                Assertions.assertThrows(EventOrganizerException.class,
                        () -> eventService.deleteEvent(event));

        assertTrue(thrown.getMessage().contentEquals("Event does not exist"));
    }

    @Test
    void GetAllPendingEvents_Should_ReturnListOfEvents() {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().plusYears(1));
        event.setStatus(Event.NONE_STATUS);
        event.setId(1L);

        Event event2 = new Event();
        event.setName("EventTest2");
        event.setTime(LocalDateTime.now().plusYears(1));
        event.setStatus(Event.NONE_STATUS);
        event.setId(2L);

        List<Event> eventsList = new ArrayList();
        eventsList.add(event);
        eventsList.add(event2);

        EventRepository eventRepository = mock(EventRepository.class);

        EventService eventService = new EventService(eventRepository);

        when(eventService.getAllPendingEvents()).thenReturn(eventsList);

        Assertions.assertTrue(eventsList.size() == 2);
        Assertions.assertTrue(eventsList.stream().allMatch(event1 -> event.getStatus().equals(Event.NONE_STATUS)));
    }

    @Test
    void GivenEvent_WhenChangeStatus_ThenStatusIsChangedSuccessfully()
    {
        Event event = new Event();
        event.setName("EventTest");
        event.setTime(LocalDateTime.now().plusYears(1));
        event.setStatus(Event.NONE_STATUS);
        event.setId(1L);
        List<Event> eventsList = new ArrayList();
        eventsList.add(event);

        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.findAll()).thenReturn(eventsList);

        eventService.changeEventStatus(event.getId(), Event.ACCEPTED_STATUS);

        Event result = eventRepository.findAll().stream().findFirst().get();

        Assertions.assertTrue(result.getStatus().equals(Event.ACCEPTED_STATUS));
    }
}
