package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.exception.UserNotAdminException;
import com.event.organizer.api.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/admin")
@AllArgsConstructor
@CrossOrigin
public class AdminController {
    public final AdminService adminService;

    public void changeAccountRole(
            String adminUserEmail,
            String editedUserEmail,
            AppUserRole role
    ) throws UserNotAdminException {
        adminService.changeAccountRole(adminUserEmail, editedUserEmail, role);
    }

    public void changeAccountStatus(
            String adminUserEmail,
            String editedUserEmail,
            Boolean status
    ) throws UserNotAdminException {
        adminService.changeAccountStatus(adminUserEmail, editedUserEmail, status);
    }
}
