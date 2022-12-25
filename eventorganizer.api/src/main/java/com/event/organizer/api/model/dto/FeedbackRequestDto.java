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
    private final Integer min = 1;
    private final Integer max = 5;
    private Integer rating;
    private String comment;
    private Long eventId;

    public Integer setBoundedRating(Integer rating) {
        Integer boundedRating = 0;

        if (rating >= max) {
            boundedRating = max;
        }
        else if (rating <= min) {
            boundedRating = min;
        }
        else {
            boundedRating = rating;
        }
        return boundedRating;
    }
}
