package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import lombok.AllArgsConstructor;
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/account")
@AllArgsConstructor
@CrossOrigin
public class AccountController {

    private final AppUserService userService;

    public void editAccount(AppUser account) {
        userService.editAccount(account);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("blockUser")
    public void blockUser(Principal user, String emailOfUserToBlock) {
        userService.blockUser(user.getName(), emailOfUserToBlock);
    }
}
