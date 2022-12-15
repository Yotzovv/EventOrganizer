package com.event.organizer.api.data;

import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/events")
public class EventsResource {

    @GetMapping
    public List<Event> getEvents() {
        return Arrays.asList(
            new Event("Sofia", 3),
            new Event("Pazardzhik", 4)
        );
    }

    @GetMapping("/{event}")
    public Event getEvent(@PathVariable("location") final String location) {
        return new Event(location, 3);
    }

    private static class Event {
        @ApiModelProperty(value = "Location of the event.")
        private String location;
        @ApiModelProperty(value = "The number of attendees that will be present at the event.")
        private Integer attendees;

        public Event() {
        }

        public Event(String location, Integer attendees) {
            this.location = location;
            this.attendees = attendees;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Integer getAttendees() {
            return attendees;
        }

        public void setAttendees(Integer attendees) {
            this.attendees = attendees;
        }
    }
}

