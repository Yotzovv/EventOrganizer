package com.event.organizer.api.model;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Comment {
    private long id;
    private String content;
    private LocalDateTime createdDate;
}
