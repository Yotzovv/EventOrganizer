package com.event.organizer.api;

import com.event.organizer.api.appuser.AppUser;
import com.event.organizer.api.appuser.AppUserRole;
import com.event.organizer.api.appuser.AppUserService;
import com.event.organizer.api.controller.AdminController;
import com.event.organizer.api.exception.UserNotAdminException;
import com.event.organizer.api.model.Comment;
import com.event.organizer.api.model.Event;
import com.event.organizer.api.repository.EventRepository;
import com.event.organizer.api.service.AdminService;
import com.event.organizer.api.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(
        classes = {
                EventRepository.class,
                EventService.class,
                Event.class, Comment.class,
                AdminService.class,
                AdminController.class
        }
)
@SpringBootTest(properties = "spring.main.lazy-initialization=true",
        classes = {
                EventRepository.class,
                EventService.class,
                Event.class,
                Comment.class,
                AdminService.class,
                AdminController.class
        }
)
public class AdminTests {

    @Test
    void GivenNonAdmin_WhenChangingAccountRole_ThenThrowsException() throws Exception {
        AppUserService appUserService = mock(AppUserService.class);
        AdminService adminService = mock(AdminService.class);

        AppUser user = new AppUser("user", "userUsername", "userEmail", "userPassword", "Sofia",
                Collections.singleton(new AppUserRole(AppUserRole.USER)));

        AppUser editedUser = new AppUser("editedUser", "editedUserUsername", "editedUserEmail", "Sofia",
                "editedUserPassword",
                Collections.singleton(new AppUserRole(AppUserRole.USER)));

        when(appUserService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(appUserService.findUserByEmail(editedUser.getEmail())).thenReturn(Optional.of(editedUser));

        UserNotAdminException throwable = new UserNotAdminException("User is not an admin.");

        String emailOfEditedUser = editedUser.getEmail();
        AppUserRole appUserRole = new AppUserRole(AppUserRole.CLIENT);

        doThrow(throwable).when(adminService).changeAccountRole(user.getEmail(), emailOfEditedUser, appUserRole);

        UserNotAdminException thrown = Assertions.assertThrows(UserNotAdminException.class, () -> adminService.changeAccountRole(
                user.getEmail(),
                editedUser.getEmail(),
                new AppUserRole(AppUserRole.CLIENT)
        ));
        
        Assertions.assertTrue(thrown.getMessage().contentEquals("User is not an admin."));
    }

    @Test
    void GivenNonAdmin_WhenChangingStatus_ThenThrowsException() throws Exception {
        AppUserService appUserService = mock(AppUserService.class);
        AdminService adminService = mock(AdminService.class);

        AppUser user = new AppUser("user", "userUsername", "userEmail", "userPassword", "Sofia",
                Collections.singleton(new AppUserRole(AppUserRole.USER)));

        AppUser editedUser = new AppUser("editedUser", "editedUserUsername", "editedUserEmail", "Sofia",
                "editedUserPassword",
                Collections.singleton(new AppUserRole(AppUserRole.USER)));

        when(appUserService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(appUserService.findUserByEmail(editedUser.getEmail())).thenReturn(Optional.of(editedUser));

        UserNotAdminException throwable = new UserNotAdminException("User is not an admin.");

        String emailOfEditedUser = editedUser.getEmail();

        doThrow(throwable).when(adminService).changeAccountStatus(user.getEmail(), emailOfEditedUser, false);

        UserNotAdminException thrown = Assertions.assertThrows(UserNotAdminException.class, () -> adminService.changeAccountStatus(
                user.getEmail(),
                editedUser.getEmail(),
                false
        ));

        Assertions.assertTrue(thrown.getMessage().contentEquals("User is not an admin."));
    }
}
