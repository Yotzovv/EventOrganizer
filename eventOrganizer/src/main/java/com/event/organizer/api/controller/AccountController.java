package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
@AllArgsConstructor
@CrossOrigin
public class AccountController {

    private final AppUserService userService;

    public void EditAccount(AppUser account){
         userService.EditAccount(account);
    }
}
