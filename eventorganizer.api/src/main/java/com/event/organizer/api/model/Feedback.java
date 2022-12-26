package com.event.organizer.api.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Feedback {

    @Id
    @SequenceGenerator(
            name = "feedback_sequence",
            sequenceName = "feedback",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "feedback_sequence"
    )
    private Integer rating;
    private String comment;
    private LocalDateTime createdDate;
    private String ownerUsername;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
