package com.event.organizer.api.service;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.UserRepository;
import com.event.organizer.api.exception.UserNotAdminException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public static final String USER_NOT_ADMIN_MESSAGE = "User is not an admin.";
    public static final String USER_NOT_FOUND_MESSAGE = "User is not found.";


    private void checkIfUsersExistAndHavePrivileges(Optional<AppUser> currentUser, Optional<AppUser> editedUser) throws Exception {
        if (currentUser.isEmpty() || editedUser.isEmpty()) {
            throw new IllegalStateException(USER_NOT_FOUND_MESSAGE);
        }

        if (currentUser.get().getRoles().contains(AppUserRole.ADMIN)) {
            throw new UserNotAdminException(USER_NOT_ADMIN_MESSAGE);
        }
    }

    public void changeAccountRole(String currentUserEmail, String editedUserEmail, AppUserRole role)
            throws Exception {
        Optional<AppUser> currentUser = userRepository.findByEmail(currentUserEmail);
        Optional<AppUser> editedUser = userRepository.findByEmail(editedUserEmail);
        checkIfUsersExistAndHavePrivileges(currentUser, editedUser);

        editedUser.get().setRoles(Collections.singleton(role));
    }

    public void changeAccountStatus(String currentUserEmail, String editedUserEmail, Boolean status)
            throws Exception {
        Optional<AppUser> currentUser = userRepository.findByEmail(editedUserEmail);
        Optional<AppUser> editedUser = userRepository.findByEmail(editedUserEmail);
        checkIfUsersExistAndHavePrivileges(currentUser, editedUser);
        editedUser.get().setEnabled(status);
    }
}


