package com.event.organizer.api.model.dto;
/**Event request DTO. Makes a model for the database.*/
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
public class EventRequestDto {
    private Long id;
    @NotNull
    private String name;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime startDate;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
        private LocalDateTime endDate;
    private String status;
    private String description;
    private String location;
}
