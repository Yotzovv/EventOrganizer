package com.event.organizer.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackRequestDto {
    private static final Integer MIN = 1;
    private static final Integer MAX = 5;
    private Integer rating;
    private String comment;
    private Long eventId;

    public void setRating(Integer rating) {
        this.rating = rating;

        if (rating >= MAX) {
            this.rating = MAX;
        }
        else if (rating <= MIN) {
            this.rating = MIN;
        }
    }
}
