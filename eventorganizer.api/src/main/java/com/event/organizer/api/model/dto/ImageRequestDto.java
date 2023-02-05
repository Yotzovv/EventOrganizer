package com.event.organizer.api.model.dto;
/**Image request DTO. Makes a model for the database.*/
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageRequestDto {
    private MultipartFile file;
    private Long eventId;
}
