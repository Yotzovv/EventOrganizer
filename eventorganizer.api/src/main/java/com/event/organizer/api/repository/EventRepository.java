package com.event.organizer.api.repository;

import com.event.organizer.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
