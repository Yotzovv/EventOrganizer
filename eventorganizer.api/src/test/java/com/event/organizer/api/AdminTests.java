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
    void TryingToChangeAccRole_WhenNotAdmin_ShouldThrowException() throws UserNotAdminException {
        AppUserService appUserService = mock(AppUserService.class);
        AdminController adminController = mock(AdminController.class);

        AppUser user = new AppUser(
                "user",
                "userUsername",
                "userEmail",
                "userPassword",
                AppUserRole.USER
        );

        AppUser editedUser = new AppUser(
                "editedUser",
                "editedUserUsername",
                "editedUserEmail",
                "editedUserPassword",
                AppUserRole.USER
        );

        when(appUserService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(appUserService.findUserByEmail(editedUser.getEmail())).thenReturn(Optional.of(editedUser));

        UserNotAdminException throwable = new UserNotAdminException("User is not an admin.");

        String emailOfUser = user.getEmail();
        String emailOfEditedUser = editedUser.getEmail();
        AppUserRole appUserRole = AppUserRole.CLIENT;

        doThrow(throwable).when(adminController).changeAccountRole(emailOfUser, emailOfEditedUser, appUserRole);

        UserNotAdminException thrown = Assertions.assertThrows(UserNotAdminException.class, () -> adminController.changeAccountRole(
                user.getEmail(),
                editedUser.getEmail(),
                AppUserRole.CLIENT
        ));

        Assertions.assertTrue(thrown.getMessage().contentEquals("User is not an admin."));
    }

    @Test
    void TryingToChangeAccStatus_WhenNotAdmin_ShouldThrowException() throws UserNotAdminException {
        AppUserService appUserService = mock(AppUserService.class);
        AdminController adminControllerV1 = mock(AdminController.class);

        AppUser user = new AppUser(
                "user",
                "userUsername",
                "userEmail",
                "userPassword",
                AppUserRole.USER
        );

        AppUser editedUser = new AppUser(
                "editedUser",
                "editedUserUsername",
                "editedUserEmail",
                "editedUserPassword",
                AppUserRole.USER
        );

        when(appUserService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(appUserService.findUserByEmail(editedUser.getEmail())).thenReturn(Optional.of(editedUser));

        UserNotAdminException throwable = new UserNotAdminException("User is not an admin.");

        String emailOfUser = user.getEmail();
        String emailOfEditedUser = editedUser.getEmail();

        doThrow(throwable).when(adminControllerV1).changeAccountStatus(emailOfUser, emailOfEditedUser, false);

        UserNotAdminException thrown = Assertions.assertThrows(UserNotAdminException.class, () -> adminControllerV1.changeAccountStatus(
                user.getEmail(),
                editedUser.getEmail(),
                false
        ));

        Assertions.assertTrue(thrown.getMessage().contentEquals("User is not an admin."));
    }
}
