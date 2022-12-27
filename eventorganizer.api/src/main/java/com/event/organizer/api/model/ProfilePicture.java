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
public class ProfilePicture {
    @Id
    @SequenceGenerator(
            name = "profilePicture_sequence",
            sequenceName = "profilePicture_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "profilePicture_sequence"
    )
    private long id;
    private String url;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
