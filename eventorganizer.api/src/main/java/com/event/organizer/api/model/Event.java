package com.event.organizer.api.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import com.event.organizer.api.appuser.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
@Table(name = "events")
public class Event {

    public static final String ACCEPTED_STATUS = "ACCEPTED";
    public static final String NONE_STATUS = "NONE";
    public static final String REJECTED_STATUS = "REJECTED";
    public static final String PENDING_STATUS = "PENDING";
    public static final Set<String> STATUSES =
        new HashSet<>(Arrays.asList(NONE_STATUS, ACCEPTED_STATUS, REJECTED_STATUS, PENDING_STATUS));

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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String description;
    private String location;

    private String eventType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser creator;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="event_id")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "events")
    private List<AppUser> appUsers;

    @ManyToMany
    @CollectionTable(name = "event_interested", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "event_interested")
    private List<AppUser> usersInterested;
 
    @ManyToMany(mappedBy = "events")
    @ElementCollection
    @CollectionTable(name = "event_goings", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "event_going")
    @JsonIgnoreProperties("usersGoing")
    private List<AppUser> usersGoing;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_images", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "event_images")
    private List<Image> images;

    @ElementCollection
    @CollectionTable(name = "event_feedbacks", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "event_feedbacks")
    private List<Feedback> feedbacks;
}


