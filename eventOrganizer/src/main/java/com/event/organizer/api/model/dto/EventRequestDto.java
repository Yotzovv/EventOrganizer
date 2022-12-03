package com.event.organizer.api.model.dto;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
public class EventRequestDto {
    // TODO: Add Description
    private Long id;
    @NotNull
    private String name;
    private LocalDateTime localDateTime;    // TODO: Rename to Start Date, Add also End Date
    private String status;
}
