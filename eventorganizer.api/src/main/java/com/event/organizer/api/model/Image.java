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
public class Image {

    @Id
    @SequenceGenerator(
            name = "image_sequence",
            sequenceName = "image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_sequence"
    )
    private long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String url;
    private LocalDateTime createdDate;
    private String ownerUsername;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}

