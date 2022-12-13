package com.event.organizer.api.filter.controller;

import com.event.organizer.api.model.dto.EventRequestDto;
import com.event.organizer.api.model.dto.LoginBody;
import com.event.organizer.api.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
@AllArgsConstructor
@CrossOrigin
public class LoginController {

    private LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginBody loginBody) {
        return loginService.loginUser(loginBody);
    }

}
