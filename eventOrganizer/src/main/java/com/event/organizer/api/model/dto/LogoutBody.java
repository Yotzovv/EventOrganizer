package com.event.organizer.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutBody {
    private String email;
    private String token;
}
