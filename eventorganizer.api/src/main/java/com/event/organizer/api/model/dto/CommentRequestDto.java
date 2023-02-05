package com.event.organizer.api.model.dto;
/**Comment request DTO. Makes a model for the database.*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDto {
    private String comment;
    private Long eventId;
}
