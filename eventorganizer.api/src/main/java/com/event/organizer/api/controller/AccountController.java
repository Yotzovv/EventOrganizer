package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.model.dto.AccountRolesRequestDto;
import com.event.organizer.api.model.dto.AccountStatusRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping("/me")
    public AppUser getLoggedInUser(Principal user) {
        return userService.findValidatedUser(user.getName());
    }

    @PutMapping("/blockUser")
    public void blockUser(Principal user, @RequestBody String emailOfUserToBlock) {
        userService.blockUser(user.getName(), emailOfUserToBlock);
    }

    @PutMapping("/unblockUser")
    public void unblockUser(Principal user, @RequestBody String emailOfUserToBlock) {
        userService.unblockUser(user.getName(), emailOfUserToBlock);
    }

    @PutMapping("/changeRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser changeAccountRole(@RequestBody AccountRolesRequestDto accountRolesRequestDto, Principal user) {
        return userService.changeAccountRole(accountRolesRequestDto, user.getName());
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AppUser changeAccountStatus(@RequestBody AccountStatusRequestDto accountStatusRequestDto, Principal user) {
        return userService.changeAccountStatus(user.getName(), accountStatusRequestDto);
    }

    @PutMapping("/addProfilePicture")
    public void uploadProfilePicture(@RequestBody String profilePictureUrl, Principal user) {
        // userService.uploadProfilePicture(profilePictureUrl, user.getName());
    }
}
