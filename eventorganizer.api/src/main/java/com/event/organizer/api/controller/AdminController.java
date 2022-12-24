package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "api/v1/admin")
@AllArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/changeRole")
    public void changeAccountRole(Principal principal, String editedUserEmail, AppUserRole role)
            throws Exception {
        adminService.changeAccountRole(principal.getName(), editedUserEmail, role);
    }

    @PutMapping("/changeStatus")
    public void changeAccountStatus(Principal principal, String editedUserEmail, Boolean status)
            throws Exception {
        adminService.changeAccountStatus(principal.getName(), editedUserEmail, status);
    }
}
