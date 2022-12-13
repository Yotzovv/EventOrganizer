package com.event.organizer.api.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Event {

    public static final String ACCEPTED_STATUS = "Accepted";
    public static final String NONE_STATUS = "None";
    public static final String REJECTED_STATUS = "Rejected";

    public static final Set<String> STATUSES =
        new HashSet<>(Arrays.asList(NONE_STATUS, ACCEPTED_STATUS, REJECTED_STATUS));

    @Id
    @SequenceGenerator(
        name = "event_sequence",
        sequenceName = "event_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "event_sequence"
    )
    private Long id;
    private String name;
    private LocalDateTime time;
    private String status;
    // start time , end time. location. AppUser

    // return token check token in request
    // if expired

    //api/v1/login   check token


}
