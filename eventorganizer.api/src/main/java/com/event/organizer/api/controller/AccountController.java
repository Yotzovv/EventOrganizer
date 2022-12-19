package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
@AllArgsConstructor
@CrossOrigin
public class AccountController {

    private final AppUserService userService;

    public void editAccount(AppUser account){
         userService.editAccount(account);
    }

    @GetMapping("getAllUsers")
    public List<AppUser> getAllUsers() {
       return userService.getAllUsers();
    }
}
