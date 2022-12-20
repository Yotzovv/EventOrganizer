package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.exception.UserNotAdminException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public static final String USER_NOT_ADMIN_MESSAGE = "User is not an admin.";
    public static final String USER_NOT_FOUND_MESSAGE = "User is not found.";

    private Boolean isUserAdmin(String email) {
        Optional<AppUser> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getRole() == AppUserRole.ADMIN;
        } else throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
    }

    public void changeAccountRole(
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

    public void changeAccountStatus(
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


