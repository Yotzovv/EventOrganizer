package com.event.organizer.api.controller;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        }
        else throw new IllegalStateException("User is not found.");
    }
}
