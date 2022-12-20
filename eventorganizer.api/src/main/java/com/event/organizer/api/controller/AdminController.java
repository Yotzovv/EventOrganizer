package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.exception.UserNotAdminException;
import com.event.organizer.api.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.event.organizer.api.service.AdminService.USER_NOT_ADMIN_MESSAGE;
import static com.event.organizer.api.service.AdminService.USER_NOT_FOUND_MESSAGE;

@RestController
@RequestMapping(path = "api/v1/admin")
@AllArgsConstructor
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    private final UserRepository userRepository;

    private Boolean isUserAdmin(String email) {
        Optional<AppUser> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getRole() == AppUserRole.ADMIN;
        } else throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
    }

    private void changeAccountRole(
            String adminUserEmail,
            String editedUserEmail,
            AppUserRole role
    ) throws UserNotAdminException {
        Optional<AppUser> admin = userRepository.findByEmail(adminUserEmail);
        Optional<AppUser> editedUser = userRepository.findByEmail(editedUserEmail);
        if (admin.isPresent()) {
            if (isUserAdmin(admin.get().getEmail())) {
                if (editedUser.isPresent()) {
                    editedUser.get().setRole(role);
                } else throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
            } else throw new UserNotAdminException(USER_NOT_ADMIN_MESSAGE);
        } else throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
    }

    private void changeAccountStatus(
            String adminUserEmail,
            String editedUserEmail,
            Boolean status
    ) throws UserNotAdminException {
        Optional<AppUser> admin = userRepository.findByEmail(adminUserEmail);
        Optional<AppUser> editedUser = userRepository.findByEmail(editedUserEmail);
        if (admin.isPresent()) {
            if (isUserAdmin(admin.get().getEmail())) {
                if (editedUser.isPresent()) {
                    editedUser.get().setEnabled(status);
                } else throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
            } else throw new UserNotAdminException(USER_NOT_ADMIN_MESSAGE);
        } else throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
    }
}
