package com.event.organizer.api.filter.controller;

import com.event.organizer.api.exception.EventOrganizerException;
import com.event.organizer.api.model.dto.LogoutBody;
import com.event.organizer.api.service.LogoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/logout")
@AllArgsConstructor
@CrossOrigin
public class LogoutController {

    private LogoutService logoutService;

    @PostMapping
    public String logout(@RequestBody LogoutBody logoutBody) throws EventOrganizerException {
        return logoutService.logoutUser(logoutBody);
    }
}
