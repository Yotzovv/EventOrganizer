package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.model.dto.AccountRolesRequestDto;
import com.event.organizer.api.model.dto.AccountStatusRequestDto;
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

    @PutMapping("/blockUser")
    public void blockUser(Principal user, String emailOfUserToBlock) {
        userService.blockUser(user.getName(), emailOfUserToBlock);
    }

    @PutMapping("/changeRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser changeAccountRole(@RequestBody AccountRolesRequestDto accountRolesRequestDto, Principal user) {
        return userService.changeAccountRole(user.getName(), accountRolesRequestDto);
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser changeAccountStatus(@RequestBody AccountStatusRequestDto accountStatusRequestDto, Principal user) {
        return userService.changeAccountStatus(user.getName(), accountStatusRequestDto);
    }
}
